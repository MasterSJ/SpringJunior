package cn.wws.springjunior.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.base.Joiner;

/**  
* @ClassName: InjectObject  
* @Description: ע�����  
* @author songjun 
* @date 2018��4��11��  
*    
*/
public class BeanFactory {
    /**    
    * @Description: ��object�б���ǿ��ǵ�fieldע���ʼ��ֵ. 
    * @author songjun  
    * @date 2018��4��13��   
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
    * @Description: ͨ����Դ���ƻ�����Ӧ��class. 
    * @author songjun  
    * @date 2018��4��11��   
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
    * @Description: ͨ����Դ���Ƶõ���Ӧ��object����. 
    * @author songjun  
    * @date 2018��4��12��   
    * @param beanName
    * @return
    */ 
    public static <T> T getBean(String beanName) {
        Class<?> clazz = null;
        Object obj = null;
        
        if (beanName != null && beanName.contains(".")) {
            /**�����beanNameΪȫ·��*/
            try {
                clazz = Class.forName(beanName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /**�����beanNameΪ��ǿ��ǵ�valueֵ*/
            clazz = getEnhancedClass(beanName);
        }
        if (clazz == null) {
            throw new RuntimeException(Joiner.on("").join(beanName, "ע��ʧ�ܣ������Ƿ������ɨ���·��", 
                    AnnotationParse.getAppointedPackage(), "֮��"));
        }
        try {
            if (clazz.isInterface()) {
                /**�ǽӿ�*/
                throw new RuntimeException(Joiner.on("").join(beanName, "�ǽӿ����ͣ�������ע��ʱָ��һ��ʵ�������ʵ����"));
            } else if (Modifier.isAbstract(clazz.getModifiers())) {
                /**�ǳ�����*/
                throw new RuntimeException(Joiner.on("").join(beanName, "�ǳ����࣬������ע��ʱָ��һ��ʵ�������ʵ����"));
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
