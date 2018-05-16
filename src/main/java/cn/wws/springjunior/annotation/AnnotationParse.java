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

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Joiner;

import cn.wws.springjunior.ioc.EnhanceClass;
import cn.wws.springjunior.ioc.EnhanceField;
import cn.wws.springjunior.ioc.EnhanceMethod;


/**  
* @ClassName: AnnotationParse  
* @Description: 注解解析类
* @author songjun 
* @date 2018年4月11日  
*    
*/
public class AnnotationParse {
    private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationParse.class);
    /**原始包根路径*/
    private static final String ORIGIN_ROOT_PACKAGE = "cn.wws.springjunior";
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
    /**被sjAspect注解标记的方法*/
    private static Map<String, EnhanceClass> sjAspectMap = new HashMap<String, EnhanceClass>();
    
    public static Map<String, EnhanceClass> getSjClassMap() {
        return sjClassMap;
    }
    
    public static Map<String, EnhanceMethod> getSjMethodMap() {
        return sjMethodMap;
    }
    
    public static Map<String, EnhanceField> getSjFieldMap() {
        return sjFieldMap;
    }
    
    public static boolean init(String appointedPackageName) {
        boolean ret = true;
        try {
            setAppointedPackage(appointedPackageName);
            List<Class<?>> allClassList = getPackageClass(ORIGIN_ROOT_PACKAGE);
            /**在原始根包路径上扫描基础注解*/
            fillAnnotationMap(allClassList);
            if (appointedPackage != null && !"".equals(appointedPackage.trim())) {
                List<Class<?>> appoitedAllClassList = getPackageClass(appointedPackage);
                /**合并原始根包路径和指定包根路径扫描到的class*/
                mergeList(allClassList, appoitedAllClassList);
                appoitedAllClassList = null;
            } else {
                throw new RuntimeException("扫描根路径为空，必须设置");
            }
            /**获取所有class中有增强标记的资源*/
            fillEnhance(allClassList);
            allClassList = null;
        } catch (Exception e) {
            ret = false;
            throw e;
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
        fillSjClassMap(list);
        fillEnhanceMethodMap(list);
        fillEnhanceFieldMap(list);
    }
    
    /**    
    * @Description: 将originList和extraList合并到originList里. 
    * @author songjun  
    * @date 2018年4月12日   
    * @param originList
    * @param extraList
    */ 
    private static void mergeList(List<Class<?>> originList, List<Class<?>> extraList) {
        if (originList != null) {
            if (extraList != null) {
                for (Class<?> clazz : extraList) {
                    if (!originList.contains(clazz)) {
                        originList.add(clazz);
                    }
                }
            }
        } else {
            originList =  extraList;
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
    * @Description: 装载注解类集合. 
    * @author songjun  
    * @date 2018年4月11日   
    * @param list
    * @param set
    */ 
    @SuppressWarnings("unchecked")
    private static void fillAnnotationMap(List<Class<?>> list) {
          if (list == null || list.isEmpty()) {
              return;
          }
          AnnotationCollection annotationCollection = AnnotationCollection.getInstance();
          for (Class<?> clazs : list) {
              if (clazs.isAnnotation()) {
                  java.lang.annotation.Target an = clazs.getAnnotation(java.lang.annotation.Target.class);
                  ElementType[] et = an.value();
                  if (ElementType.TYPE.equals(et[0])) {
                      annotationCollection.putClassAnnotation((Class<? extends Annotation>) clazs);
                  } else if (ElementType.METHOD.equals(et[0])) {
                      annotationCollection.putMethodAnnotation((Class<? extends Annotation>) clazs);
                  } else if (ElementType.FIELD.equals(et[0])) {
                      annotationCollection.putFieldAnnotation((Class<? extends Annotation>) clazs);
                  }
              }
          }
          LOGGER.debug("annotationCollection={}", annotationCollection);
      }
      
    /**    
    * @Description: 装载增强标记类集合. 
    * @author songjun  
    * @date 2018年4月11日   
    * @param list
    * @param set
    * @throws ClassNotFoundException
    */ 
    public static void fillSjClassMap(List<Class<?>> list) {
        Set<Class<? extends Annotation>> classAnnotationSet = AnnotationCollection.getInstance().getClassAnnotation();
        for (Class<?> clazz : list) {
          for (Class<? extends Annotation> annotation : classAnnotationSet) {
              if (clazz.isAnnotationPresent(annotation)) {
                  EnhanceClass enhanceClass = new EnhanceClass(annotation.getName(), clazz);
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
                      sjClassMap.put(resourceName, enhanceClass);
                  } else if (SjAspect.class.getName().equals(annotation.getName())) {
                      SjAspect sj = clazz.getAnnotation(SjAspect.class);
                      String resourceName = sj.value();
                      if (resourceName == null || resourceName.trim().equals("")) {
                          throw new RuntimeException(Joiner.on("").join("资源名称命名不能为空：", clazz.getName()));
                      } else if (resourceName.contains(".")) {
                          throw new RuntimeException(Joiner.on("").join("资源名称命名不能包含\".\"：", clazz.getName()));
                      }
                      enhanceClass.setAnnotationValue(resourceName);
                      if (sjAspectMap.get(resourceName) != null) {
                          throw new RuntimeException(Joiner.on("").join("资源名称命名重复：", clazz.getName(), " & ", 
                                  sjAspectMap.get(resourceName).getAnnotationTarget().getName()));
                      }
                      sjAspectMap.put(resourceName, enhanceClass);
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
                            EnhanceMethod enhanceMethod = new EnhanceMethod(annotation.getName(), method);
                            sjBeforeMap.put(Joiner.on("").join(clazz.getName(), ".", method.getName()), enhanceMethod);
                        } else if (annotation.getName().equals(SjAfter.class.getName())) {
                            EnhanceMethod enhanceMethod = new EnhanceMethod(annotation.getName(), method);
                            sjAfterMap.put(Joiner.on("").join(clazz.getName(), ".", method.getName()), enhanceMethod);
                        }
                    }
                }
            }
            
        }
        LOGGER.debug("sjMethodMap={}", sjMethodMap);
        LOGGER.debug("sjBeforeMap={}", sjBeforeMap);
        LOGGER.debug("sjAfterMap={}", sjAfterMap);
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
                            EnhanceField enhanceField = new EnhanceField(annotation.getName(), field, clazz.getName(), resourceName);
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
