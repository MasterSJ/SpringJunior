/**
 * 
 */
package cn.wws.springjunior.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
* @ClassName: SjPointCut  
* @Description: aop 切入方法注解  
* @author songjun 
* @date 2018年4月24日  
*    
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SjPointCut {
    String value();
}
