package cn.wws.springjunior.ioc;

import com.google.common.base.Joiner;

/**  
* @ClassName: EnhanceClass  
* @Description: 增强类结构  
* @author songjun 
* @date 2018年4月11日  
*    
*/
public class EnhanceClass {
    private String annotationValue;
    private String anotationIsSingleton;
    private Class<?> annotationTarget;


    public EnhanceClass(Class<?> annoTarget) {
        this.annotationTarget = annoTarget;
    }
    
    
    public String getAnotationIsSingleton() {
        return anotationIsSingleton;
    }
    public void setAnotationIsSingleton(String anotationIsSingleton) {
        this.anotationIsSingleton = anotationIsSingleton;
    }

    public String getAnnotationValue() {
        return annotationValue;
    }
    public void setAnnotationValue(String annotationValue) {
        this.annotationValue = annotationValue;
    }
    
    public Class<?> getAnnotationTarget() {
        return annotationTarget;
    }
    public void setAnnotationTarget(Class<?> annotationTarget) {
        this.annotationTarget = annotationTarget;
    }
    
    public String toString() {
        return Joiner.on("").join("{annotationValue=", annotationValue == null ? "null" : annotationValue, 
                "; anotationIsSingleton=", anotationIsSingleton == null ? "null" : anotationIsSingleton, 
                "; annotationTarget=", annotationTarget.getName(), "}");
    }
}
