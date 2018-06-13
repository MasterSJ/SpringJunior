package cn.wws.springjunior.ioc;

import java.lang.reflect.Method;

import com.google.common.base.Joiner;

/**  
* @ClassName: EnhanceField  
* @Description: 增强方法结构
* @author songjun 
* @date 2018年4月11日  
*    
*/
public class EnhanceMethod {
    private String annotationValue;
    private Method annotationTarget;
    
    public EnhanceMethod(Method annoTarget) {
        this.annotationTarget = annoTarget;
    }
    
    public EnhanceMethod(String annotationValue, Method annoTarget) {
        this.annotationValue = annotationValue;
        this.annotationTarget = annoTarget;
    }
    
    public Method getAnnotationTarget() {
        return annotationTarget;
    }
    public void setAnnotationTarget(Method annotationTarget) {
        this.annotationTarget = annotationTarget;
    }
    public String getAnnotationValue() {
        return annotationValue;
    }
    public void setAnnotationValue(String annotationValue) {
        this.annotationValue = annotationValue;
    }
    
    public String toString() {
        return Joiner.on("").join("{annotationValue=", 
                annotationValue == null ? "" : annotationValue, "; annotationTarget=", annotationTarget, "}");
    }
}
