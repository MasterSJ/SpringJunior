package cn.wws.springjunior.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**  
* @ClassName: SjPerformanceAnalysis  
* @Description: aop 分析方法执行时间  
* @author songjun 
* @date 2018年8月3日  
*    
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SjPerformanceAnalysis {
    String value() default "";
}
