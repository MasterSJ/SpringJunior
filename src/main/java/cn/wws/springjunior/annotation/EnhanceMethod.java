package cn.wws.springjunior.annotation;

import java.lang.reflect.Method;

import com.google.common.base.Joiner;

/**  
* @ClassName: EnhanceMethod  
* @Description: 增强方法结构  
* @author songjun 
* @date 2018年4月11日  
*    
*/
public class EnhanceMethod {
    private String annotationName;
    private Method annotationTarget;
    
    public EnhanceMethod(String annoName, Method annoTarget) {
        this.annotationName = annoName;
        this.annotationTarget = annoTarget;
    }
    
    public String getAnnotationName() {
        return annotationName;
    }
    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }
    public Method getAnnotationTarget() {
        return annotationTarget;
    }
    public void setAnnotationTarget(Method annotationTarget) {
        this.annotationTarget = annotationTarget;
    }
    
    public String toString() {
        return Joiner.on("").join("{annotationName=", annotationName, "; annotationTarget=", annotationTarget, "}");
    }
}
