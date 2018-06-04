package cn.wws.springjunior.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
* @ClassName: SjField  
* @Description: 普通类注解
* @author songjun 
* @date 2018年4月11日  
*/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SjClass {
    String value();
    String isSingleton() default "true";
}
