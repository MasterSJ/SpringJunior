package cn.wws.springjunior.annotation;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

import cn.wws.springjunior.SjConfigure;
import cn.wws.springjunior.ioc.EnhanceClass;
import cn.wws.springjunior.ioc.EnhanceField;
import cn.wws.springjunior.ioc.EnhanceMethod;
import cn.wws.springjunior.ioc.SjClassMapped;
import cn.wws.springjunior.itf.SjInputOutputInterface;
import cn.wws.springjunior.util.DefultInputOutput;


/**  
* @ClassName: AnnotationParse  
* @Description: 注解解析类
* @author songjun 
* @date 2018年4月11日  
*    
*/
public class AnnotationParse {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationParse.class);
    /**用户指定包根路径*/
    private static String appointedPackage = "";
    /**被sjClass注解标记的类*/
    private static Map<String, EnhanceClass> sjClassMap = new HashMap<String, EnhanceClass>();
    /**被sjMethod注解标记的方法*/
    private static Map<String, EnhanceMethod> sjMethodMap = new HashMap<String, EnhanceMethod>();
    /**被sjField注解标记的属性*/
    private static Map<String, EnhanceField> sjFieldMap = new HashMap<String, EnhanceField>();
    /**被sjBefore注解标记的方法*/
    private static Map<String, EnhanceMethod> sjBeforeMap = new HashMap<String, EnhanceMethod>();
    /**被sjAfter注解标记的方法*/
    private static Map<String, EnhanceMethod> sjAfterMap = new HashMap<String, EnhanceMethod>();
    /**被sjPerformanceAnalysisMap注解标记的方法*/
    private static Map<String, EnhanceMethod> sjPerformanceAnalysisMap = new HashMap<String, EnhanceMethod>();
    /**被sjAspect注解标记的方法*/
    private static Map<String, EnhanceClass> sjAspectMap = new HashMap<String, EnhanceClass>();
    /**系统参数*/
    private static Map<String, Object> sjSystemParam = new HashMap<String, Object>();
    
    static {
        init(SjConfigure.getAppointedPackageName());
    }
    
    public static Map<String, EnhanceClass> getSjClassMap() {
        return sjClassMap;
    }
    
    public static Map<String, EnhanceMethod> getSjMethodMap() {
        return sjMethodMap;
    }
    
    public static Map<String, EnhanceField> getSjFieldMap() {
        return sjFieldMap;
    }
    
    public static Map<String, EnhanceMethod> getSjBeforeMap() {
        return sjBeforeMap;
    }
    
    public static Map<String, EnhanceMethod> getSjAfterMap() {
        return sjAfterMap;
    }
    
    public static Map<String, EnhanceMethod> getSjPerformanceAnalysisMap() {
        return sjPerformanceAnalysisMap;
    }
    
    public static Object getSjSystemParam(String key) {
        if (sjSystemParam != null) {
            return sjSystemParam.get(key);
        }
        return null;
    }
    
    public static boolean init(String appointedPackageName) {
        boolean ret = true;
        try {
            setAppointedPackage(appointedPackageName);
            List<Class<?>> appoitedAllClassList = null;
            if (appointedPackage != null && !"".equals(appointedPackage.trim())) {
                appoitedAllClassList = getPackageClass(appointedPackage);
            } else {
                throw new RuntimeException("扫描根路径为空，必须设置");
            }
            /**获取所有class中有增强标记的资源*/
            fillEnhance(appoitedAllClassList);
            /**填充系统参数*/
            fillSystemParam(appoitedAllClassList);
        } catch (Exception e) {
            ret = false;
            LOGGER.error("初始化异常，e={}", e);
        } 
        return ret;
    }
    
    /**    
     * @Description: 装载增强标记. 
     * @author songjun  
     * @date 2018年4月11日   
     * @param list
     * @param set
     * @throws ClassNotFoundException
     */ 
    private static void fillEnhance(List<Class<?>> list) {
        fillEnhanceClassMap(list);
        fillEnhanceMethodMap(list);
        fillEnhanceFieldMap(list);
    }
    
    private static void fillSystemParam(List<Class<?>> appoitedAllClassList) {
        ArrayList<Class> list = SystemParam.getAllClassByInterface(SjInputOutputInterface.class, appoitedAllClassList);
        if (list == null || list.isEmpty()) {
            LOGGER.error("没有找到SjInputOutputInterface的自定义实现类，使用默认实现类");
            sjSystemParam.put("defaultInputOutput", new DefultInputOutput());
        } else if (list.size() == 1) {
            Class clazz = (Class) list.get(0);
            try {
                sjSystemParam.put("defaultInputOutput", clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            Class clazz = (Class) list.get(0);
            LOGGER.error("SjInputOutputInterface存在多个自定义实现类，使用：{}", clazz);
            try {
                sjSystemParam.put("defaultInputOutput", clazz.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    
    
    /**    
    * @Description: 根据包名返回对应class列表. 
    * @author songjun  
    * @date 2018年4月11日   
    * @param packageName
    * @return classList
    */ 
    private static List<Class<?>> getPackageClass(String packageName) {
        List<Class<?>> classList = new ArrayList<Class<?>>();  
        String packageDirName = packageName.replace('.', '/');  
        Enumeration<URL> dirs = null;  
        //获取当前目录下面的所有的子目录的url  
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();  
            //得到当前url的类型  
            String protocol = url.getProtocol();  
            //如果当前类型是文件类型  
            if ("file".equals(protocol)) {
                //获取包的物理路径  
                String filePath = null;  
                try {  
                    filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                } catch (UnsupportedEncodingException e) {  
                    e.printStackTrace();  
                }
                filePath = filePath.substring(1);  
                getFilePathClasses(packageName, filePath, classList);  
            }  
         }  
         return classList;  
     }
    
     /**    
      * @Description: 将制定包下的class全部加到到classList中 . 
      * @author songjun  
      * @date 2018年4月4日   
      * @param packageName
      * @param filePath
      * @param classList
      */ 
      private static void getFilePathClasses(String packageName, String filePath, List<Class<?>> classList) {
          Path dir = Paths.get(filePath);  
          DirectoryStream<Path> stream = null;  
          try {
              //获得当前目录下的文件的stream流  
              stream = Files.newDirectoryStream(dir);  
          } catch (IOException e) {
              e.printStackTrace();  
          }  
    
          for (Path path : stream) {
              String fileName = String.valueOf(path.getFileName());
              
              if (Files.isDirectory(path)) {
                  getFilePathClasses(Joiner.on("").join(packageName, ".", fileName), 
                          Joiner.on("").join(filePath, "/", fileName), classList);
              } else if (fileName.endsWith(".class")) {
                  LOGGER.debug(Joiner.on("").join("解析文件", fileName));
                  String className = fileName.substring(0, fileName.length() - 6);  
        
                  Class<?> classes = null;  
                  try {  
                      classes = Thread.currentThread().getContextClassLoader().loadClass(Joiner.on("").join(packageName, ".", className));  
                  } catch (ClassNotFoundException e) {  
                      e.printStackTrace();  
                  }  
        
                  classList.add(classes);  
              }
          }  
      } 
      
    /**    
    * @Description: 装载增强标记类集合. 
    * @author songjun  
    * @date 2018年4月11日   
    * @param list
    * @param set
    * @throws ClassNotFoundException
    */ 
    public static void fillEnhanceClassMap(List<Class<?>> list) {
        Set<Class<? extends Annotation>> classAnnotationSet = AnnotationCollection.getInstance().getClassAnnotation();
        for (Class<?> clazz : list) {
          for (Class<? extends Annotation> annotation : classAnnotationSet) {
              if (clazz.isAnnotationPresent(annotation)) {
                  EnhanceClass enhanceClass = new EnhanceClass(clazz);
                  if (SjClass.class.getName().equals(annotation.getName())) {
                      SjClass sj = clazz.getAnnotation(SjClass.class);
                      String resourceName = sj.value();
                      if (resourceName == null || resourceName.trim().equals("")) {
                          throw new RuntimeException(Joiner.on("").join("资源名称命名不能为空：", clazz.getName()));
                      } else if (resourceName.contains(".")) {
                          throw new RuntimeException(Joiner.on("").join("资源名称命名不能包含\".\"：", clazz.getName()));
                      }
                      enhanceClass.setAnnotationValue(resourceName);
                      if (sjClassMap.get(resourceName) != null) {
                          throw new RuntimeException(Joiner.on("").join("资源名称命名重复：", clazz.getName(), " & ", 
                                  sjClassMap.get(resourceName).getAnnotationTarget().getName()));
                      }
                      String isSingleton = sj.isSingleton();
                      enhanceClass.setAnotationIsSingleton(isSingleton);
                      sjClassMap.put(resourceName, enhanceClass);
                      SjClassMapped.putMappedRelation(clazz.getName(), resourceName);
                  } else if (SjAspect.class.getName().equals(annotation.getName())) {
                      if (sjAspectMap.get(clazz.getName()) != null) {
                          throw new RuntimeException(Joiner.on("").join("资源名称命名重复：", clazz.getName(), " & ", 
                                  sjAspectMap.get(clazz.getName()).getAnnotationTarget().getName()));
                      }
                      sjAspectMap.put(clazz.getName(), enhanceClass);
                  }
              }
          }
        }
        LOGGER.debug("sjClassMap={}", sjClassMap);
        LOGGER.debug("sjAspectMap={}", sjAspectMap);
    }
    
    /**    
     * @Description: 装载增强标记方法集合. 
     * @author songjun  
     * @date 2018年4月11日   
     * @param list
     * @param set
     * @throws ClassNotFoundException
     */ 
    public static void fillEnhanceMethodMap(List<Class<?>> list) {
        Set<Class<? extends Annotation>> methodAnnotationSet = AnnotationCollection.getInstance().getMethodAnnotation();
        for (Class<?> clazz : list) {
            for (Method method : clazz.getDeclaredMethods()) {
                for (Class<? extends Annotation> annotation : methodAnnotationSet) {
                    if (method.isAnnotationPresent(annotation)) {
                        if (annotation.getName().equals(SjMethod.class.getName())) {
                            EnhanceMethod enhanceMethod = new EnhanceMethod(annotation.getName(), method);
                            sjMethodMap.put(Joiner.on("").join(clazz.getName(), ".", method.getName()), enhanceMethod);
                        } else if (annotation.getName().equals(SjBefore.class.getName())) {
                            SjBefore sj = method.getAnnotation(SjBefore.class);
                            String methodValue = sj.value();
                            EnhanceMethod enhanceMethod = new EnhanceMethod(methodValue, method);
                            sjBeforeMap.put(Joiner.on("").join(clazz.getName(), ".", method.getName()), enhanceMethod);
                        } else if (annotation.getName().equals(SjAfter.class.getName())) {
                            SjAfter sj = method.getAnnotation(SjAfter.class);
                            String methodValue = sj.value();
                            EnhanceMethod enhanceMethod = new EnhanceMethod(methodValue, method);
                            sjAfterMap.put(Joiner.on("").join(clazz.getName(), ".", method.getName()), enhanceMethod);
                        } else if (annotation.getName().equals(SjPerformanceAnalysis.class.getName())) {
                            SjPerformanceAnalysis sj = method.getAnnotation(SjPerformanceAnalysis.class);
                            String methodValue = sj.value();
                            EnhanceMethod enhanceMethod = new EnhanceMethod(methodValue, method);
                            sjPerformanceAnalysisMap.put(Joiner.on("").join(clazz.getName(), ".", method.getName()), enhanceMethod);
                        }
                    }
                }
            }
            
        }
        LOGGER.debug("sjMethodMap={}", sjMethodMap);
        LOGGER.debug("sjBeforeMap={}", sjBeforeMap);
        LOGGER.debug("sjAfterMap={}", sjAfterMap);
        LOGGER.debug("sjPerformanceAnalysisMap={}", sjPerformanceAnalysisMap);
    }
    
    /**    
     * @Description: 装载增强标记属性集合. 
     * @author songjun  
     * @date 2018年4月11日   
     * @param list
     * @param set
     * @throws ClassNotFoundException
     */ 
    public static void fillEnhanceFieldMap(List<Class<?>> list) {
        Set<Class<? extends Annotation>> fieldAnnotationSet = AnnotationCollection.getInstance().getFieldAnnotation();
        for (Class<?> clazz : list) {
            for (Field field : clazz.getDeclaredFields()) {
                for (Class<? extends Annotation> annotation : fieldAnnotationSet) {
                    if (field.isAnnotationPresent(annotation)) {
                        if (annotation.getName().equals(SjField.class.getName())) {
                            SjField sj = field.getAnnotation(SjField.class);
                            String resourceName = sj.value();
                            if (resourceName != null && resourceName.contains(".")) {
                                throw new RuntimeException(Joiner.on("").join("资源名称命名不能包含\".\"：", clazz.getName()));
                            }
                            checkJavaBaseType(clazz, field);
                            EnhanceField enhanceField = new EnhanceField(field, clazz.getName(), resourceName);
                            sjFieldMap.put(Joiner.on("").join(clazz.getName(), ".", field.getName()), enhanceField);
                        }
                    }
                }
            }
            
        }
        LOGGER.debug("sjFieldMap={}", sjFieldMap);
    }
    
    /**    
    * @Description: 检查注入的成员变量是否是基本数据类型. 
    * @author songjun  
    * @date 2018年4月13日   
    * @param clazz
    * @param field
    */ 
    private static void checkJavaBaseType(Class<?> clazz, Field field) {
        if (field.getType().getCanonicalName().equals("int")) {
            throw new RuntimeException(Joiner.on("").join("不能注入int类型的成员变量：", clazz.getName(), ".", field.getName()));
        } else if (field.getType().getCanonicalName().equals("byte")) {
            throw new RuntimeException(Joiner.on("").join("不能注入byte类型的成员变量：", clazz.getName(), ".", field.getName()));
        } else if (field.getType().getCanonicalName().equals("short")) {
            throw new RuntimeException(Joiner.on("").join("不能注入short类型的成员变量：", clazz.getName(), ".", field.getName()));
        } else if (field.getType().getCanonicalName().equals("long")) {
            throw new RuntimeException(Joiner.on("").join("不能注入long类型的成员变量：", clazz.getName(), ".", field.getName()));
        } else if (field.getType().getCanonicalName().equals("float")) {
            throw new RuntimeException(Joiner.on("").join("不能注入float类型的成员变量：", clazz.getName(), ".", field.getName()));
        } else if (field.getType().getCanonicalName().equals("double")) {
            throw new RuntimeException(Joiner.on("").join("不能注入double类型的成员变量：", clazz.getName(), ".", field.getName()));
        } else if (field.getType().getCanonicalName().equals("boolean")) {
            throw new RuntimeException(Joiner.on("").join("不能注入boolean类型的成员变量：", clazz.getName(), ".", field.getName()));
        } else if (field.getType().getCanonicalName().equals("char")) {
            throw new RuntimeException(Joiner.on("").join("不能注入char类型的成员变量：", clazz.getName(), ".", field.getName()));
        }
    }
    
    /**    
    * @Description: 设置指定扫描包路径. 
    * @author songjun  
    * @date 2018年4月11日   
    * @param packageName
    */ 
    private static void setAppointedPackage(String packageName) {
        appointedPackage = packageName;
        LOGGER.debug("appointedPackage被设置为{}", packageName);
    }
    
    /**    
    * @Description: 获取指定扫描包路径. 
    * @author songjun  
    * @date 2018年4月11日   
    * @return
    */ 
    public static String getAppointedPackage() {
        return appointedPackage;
    }
}
