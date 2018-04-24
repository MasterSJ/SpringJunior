package cn.wws.springjunior.ioc;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Joiner;

/**  
* @ClassName: AnnotationCollection  
* @Description: 注解收集器  
* @author songjun 
* @date 2018年4月11日  
*    
*/
public final class AnnotationCollection {
    private Set<Class<? extends Annotation>> classAnnotations;
    private Set<Class<? extends Annotation>> methodAnnotations;
    private Set<Class<? extends Annotation>> fieldAnnotations;
    
    private static AnnotationCollection singleInstance;
    
    private AnnotationCollection() {
        classAnnotations = new HashSet<Class<? extends Annotation>>();
        methodAnnotations = new HashSet<Class<? extends Annotation>>();
        fieldAnnotations = new HashSet<Class<? extends Annotation>>();
    }
    
    public static AnnotationCollection getInstance() {
        if (singleInstance == null) {
            Object object = new Object();
            synchronized (object) {
                if (singleInstance == null) {
                    singleInstance = new AnnotationCollection();
                }
            }
            object = null;
        }
        return singleInstance;
    }

    public void putClassAnnotation(Class<? extends Annotation> clazz) {
        classAnnotations.add(clazz);
    }
    
    public Set<Class<? extends Annotation>> getClassAnnotation() {
        return classAnnotations;
    }
    
    public void putMethodAnnotation(Class<? extends Annotation> clazz) {
        methodAnnotations.add(clazz);
    }
    
    public Set<Class<? extends Annotation>> getMethodAnnotation() {
        return methodAnnotations;
    }
    
    public void putFieldAnnotation(Class<? extends Annotation> clazz) {
        fieldAnnotations.add(clazz);
    }
    
    public Set<Class<? extends Annotation>> getFieldAnnotation() {
        return fieldAnnotations;
    }
    
    
    
    public String toString() {
        return Joiner.on("").join("{classAnnotations=", classAnnotations, "; methodAnnotations=", 
                methodAnnotations, "; fieldAnnotations=", fieldAnnotations, "}");
    }
}
