package cn.wws.springjunior.annotation;

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
    /**标记为类注解的注解的集合*/  
    private Set<Class<? extends Annotation>> classAnnotations;
    /**标记为方法注解的注解的集合*/  
    private Set<Class<? extends Annotation>> methodAnnotations;
    /**标记为属性注解的注解的集合*/  
    private Set<Class<? extends Annotation>> fieldAnnotations;
    
    private static AnnotationCollection singleInstance;
    
    private AnnotationCollection() {
        classAnnotations = new HashSet<Class<? extends Annotation>>();
        putClassAnnotation(SjClass.class);
        putClassAnnotation(SjAspect.class);
        methodAnnotations = new HashSet<Class<? extends Annotation>>();
        putMethodAnnotation(SjMethod.class);
        putMethodAnnotation(SjBefore.class);
        putMethodAnnotation(SjAfter.class);
        fieldAnnotations = new HashSet<Class<? extends Annotation>>();
        putFieldAnnotation(SjField.class);
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
