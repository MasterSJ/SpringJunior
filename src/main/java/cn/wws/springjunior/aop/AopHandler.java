package cn.wws.springjunior.aop;

import java.util.Map;

import cn.wws.springjunior.annotation.AnnotationParse;
import cn.wws.springjunior.ioc.EnhanceMethod;

/**  
* @ClassName: AopHandler  
* @Description: Aop处理器  
* @author songjun 
* @date 2018年6月13日  
*    
*/
public class AopHandler {
    /**    
     * @Description: 处理aop.
     * 这里判断是否需要做aop处理，跟aop处理中判断处理哪一个方法差不多，后续考虑简化一下 
     * @author songjun  
     * @date 2018年5月17日   
     * @param obj
     * @return
     */ 
     public static Object doAopHandle(Object obj) {
         Map<String, EnhanceMethod> methodMap = AnnotationParse.getSjBeforeMap();
         for (Map.Entry<String, EnhanceMethod> entry : methodMap.entrySet()) {
             String toIntance = entry.getValue().getAnnotationValue();
             int offset = toIntance.lastIndexOf(".");
             String className = toIntance.substring(0, offset);
             if (className.equals(obj.getClass().getName())) {
                 try {
                     return new CGLIBProxy(obj).getProxy();
                 } catch (ClassNotFoundException e) {
                     e.printStackTrace();
                 }
             }
         }
         methodMap = AnnotationParse.getSjAfterMap();
         for (Map.Entry<String, EnhanceMethod> entry : methodMap.entrySet()) {
             String toIntance = entry.getValue().getAnnotationValue();
             int offset = toIntance.lastIndexOf(".");
             String className = toIntance.substring(0, offset);
             if (className.equals(obj.getClass().getName())) {
                 try {
                     return new CGLIBProxy(obj).getProxy();
                 } catch (ClassNotFoundException e) {
                     e.printStackTrace();
                 }
             }
         }
         return obj;
     }
}
