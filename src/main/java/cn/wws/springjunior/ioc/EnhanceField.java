package cn.wws.springjunior.ioc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import cn.wws.springjunior.annotation.AnnotationParse;

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
        List<EnhanceField> fields = new ArrayList<EnhanceField>();
        Class<?> clazz = null;
        /**遍历增强标记属性*/
        for (EnhanceField enF : AnnotationParse.getSjFieldMap().values()) {
            /**遍历所有祖先类，直到查到object*/
            for (clazz = obj.getClass(); !clazz.getName().equals("java.lang.Object"); ) {
                if (fieldBelongClass(enF, clazz)) {
                    fields.add(enF);
                    break;
                }
                clazz = clazz.getSuperclass();
            }
        }
        if (fields.isEmpty()) {
            fields = null;
        }
        return fields;
    }
    
    /*field是否属于此class*/
    private static boolean fieldBelongClass(EnhanceField enF, Class<?> clazz) {
        return clazz.getName().equals(enF.getParentClassName());
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
