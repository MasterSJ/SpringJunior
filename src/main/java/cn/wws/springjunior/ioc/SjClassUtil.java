package cn.wws.springjunior.ioc;

import cn.wws.springjunior.annotation.AnnotationParse;

public class SjClassUtil {
    /**    
    * @Description: 判断是否注解为单例. 
    * @author songjun  
    * @date 2018年6月4日   
    * @param value
    * @return
    */ 
    public static boolean isSingleton(String value) {
        boolean ret = false;
        if (value.contains(".")) {
            value = SjClassMapped.getFullName(value);
        }
        String isSingleton = AnnotationParse.getSjClassMap().get(value).getAnotationIsSingleton();
        if ("true".equals(isSingleton)) {
            ret = true;
        }
        return ret;
    }
}
