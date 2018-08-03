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
        classAnnotations.add(SjClass.class);
        classAnnotations.add(SjAspect.class);
        methodAnnotations = new HashSet<Class<? extends Annotation>>();
        methodAnnotations.add(SjMethod.class);
        methodAnnotations.add(SjBefore.class);
        methodAnnotations.add(SjAfter.class);
        fieldAnnotations = new HashSet<Class<? extends Annotation>>();
        fieldAnnotations.add(SjField.class);
    }
    
    public static AnnotationCollection getInstance() {
        if (singleInstance == null) {
            synchronized (AnnotationCollection.class) {
                if (singleInstance == null) {
                    singleInstance = new AnnotationCollection();
                }
            }
        }
        return singleInstance;
    }
    
    public Set<Class<? extends Annotation>> getClassAnnotation() {
        return classAnnotations;
    }
    
    public Set<Class<? extends Annotation>> getMethodAnnotation() {
        return methodAnnotations;
    }
    
    public Set<Class<? extends Annotation>> getFieldAnnotation() {
        return fieldAnnotations;
    }
    
    public String toString() {
        return Joiner.on("").join("{classAnnotations=", classAnnotations, "; methodAnnotations=", 
                methodAnnotations, "; fieldAnnotations=", fieldAnnotations, "}");
    }
}
