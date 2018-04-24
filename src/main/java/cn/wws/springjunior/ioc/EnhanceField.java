package cn.wws.springjunior.ioc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

/**  
* @ClassName: EnhanceField  
* @Description: 增强属性结构
* @author songjun 
* @date 2018年4月11日  
*    
*/
public class EnhanceField {
    private String annotationName;
    private Field annotationTarget;
    private String parentClassName;
    private String annotationValue;
    
    




















    public static List<EnhanceField> getEnhanceFields(Object obj) {
        List<EnhanceField> fields = null;
        Class<?> clazz = obj.getClass();
        /**遍历增强标记属性*/
        for (EnhanceField enF : AnnotationParse.getEnhanceFieldMap().values()) {
            /**遍历所有祖先类，直到查到object*/
            while (!clazz.getName().equals("java.lang.Object")) {
                if (clazz.getName().equals(enF.getParentClassName())) {
                    if (fields == null) {
                        fields = new ArrayList<EnhanceField>();
                    }
                    fields.add(enF);
                }
                clazz = clazz.getSuperclass();
            }
        }
        return fields;
    }
    
    
    
    
    
    
    
    
    

    public EnhanceField(String annoName, Field annoTarget, String parentClassName, String annotationValue) {
        this.annotationName = annoName;
        this.annotationTarget = annoTarget;
        this.parentClassName = parentClassName;
        this.annotationValue = annotationValue;
    }
    
    public String getParentClassName() {
        return parentClassName;
    }

    public void setParentClassName(String parentClassName) {
        this.parentClassName = parentClassName;
    }
    
    public String getAnnotationName() {
        return annotationName;
    }
    public void setAnnotationName(String annotationName) {
        this.annotationName = annotationName;
    }
    public Field getAnnotationTarget() {
        return annotationTarget;
    }
    public void setAnnotationTarget(Field annotationTarget) {
        this.annotationTarget = annotationTarget;
    }
    
    public String getAnnotationValue() {
        return annotationValue;
    }
    
    public void setAnnotationValue(String annotationValue) {
        this.annotationValue = annotationValue;
    }
    
    public String toString() {
        return Joiner.on("").join("{annotationName=", annotationName, "; annotationTarget=", annotationTarget,
                "; parentClassName=", parentClassName, "; annotationValue=", annotationValue, "}");
    }
}
