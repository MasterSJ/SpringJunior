package cn.wws.springjunior.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
* @ClassName: SjField  
* @Description: 普通属性注解
* @author songjun 
* @date 2018年4月11日  
*/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SjField {
    String value() default "";
}
