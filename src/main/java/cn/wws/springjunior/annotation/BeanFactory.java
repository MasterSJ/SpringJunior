package cn.wws.springjunior.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;

/**  
* @ClassName: InjectObject  
* @Description: 注入对象  
* @author songjun 
* @date 2018年4月11日  
*    
*/
public class BeanFactory {
    /**    
    * @Description: 给object中被增强标记的field注入初始化值. 
    * @author songjun  
    * @date 2018年4月13日   
    * @param object
    * @return
    * @throws IllegalArgumentException
    * @throws IllegalAccessException
    */ 
    public static boolean injectField(Object object) throws IllegalArgumentException, IllegalAccessException {
        boolean ret = true;
        List<EnhanceField> needInjectFields = EnhanceField.getEnhanceFields(object);
        if (needInjectFields == null) {
            return ret;
        }
        String beanName;
        for (EnhanceField enhanceField : needInjectFields) {
            Field field = enhanceField.getAnnotationTarget();
            if (StringUtils.isEmpty(enhanceField.getAnnotationValue())) {
                beanName = field.getType().getCanonicalName();
            } else {
                beanName = enhanceField.getAnnotationValue();
            }
            field.setAccessible(true);
            field.set(object, getBean(beanName));
        }
        return ret;
    }
    
    /**    
    * @Description: 通过资源名称获得其对应的class. 
    * @author songjun  
    * @date 2018年4月11日   
    * @param beanName
    * @return
    */ 
    public static Class<?> getEnhancedClass(String beanName) {
        Class<?> clazz = null;
        Map<String, EnhanceClass> map = AnnotationParse.getEnhanceClassMap();
        EnhanceClass enhanCla = map.get(beanName);
        if (enhanCla != null) {
            clazz = enhanCla.getAnnotationTarget();
        }
        return clazz;
    }
    
    /**    
    * @Description: 通过资源名称得到对应的object对象. 
    * @author songjun  
    * @date 2018年4月12日   
    * @param beanName
    * @return
    */ 
    public static <T> T getBean(String beanName) {
        Class<?> clazz = null;
        Object obj = null;
        
        if (beanName != null && beanName.contains(".")) {
            /**传入的beanName为全路径*/
            try {
                clazz = Class.forName(beanName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /**传入的beanName为增强标记的value值*/
            clazz = getEnhancedClass(beanName);
        }
        if (clazz == null) {
            throw new RuntimeException(Joiner.on("").join(beanName, "注入失败，请检查是否包含在扫描跟路径", 
                    AnnotationParse.getAppointedPackage(), "之中"));
        }
        try {
            if (clazz.isInterface()) {
                /**是接口*/
                throw new RuntimeException(Joiner.on("").join(beanName, "是接口类型，必须在注入时指定一个实现类对其实例化"));
            } else if (Modifier.isAbstract(clazz.getModifiers())) {
                /**是抽象类*/
                throw new RuntimeException(Joiner.on("").join(beanName, "是抽象类，必须在注入时指定一个实现类对其实例化"));
            }
            obj = clazz.newInstance();
            injectField(obj);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return (T)obj;
    }
    
}
