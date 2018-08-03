package cn.wws.springjunior.aop;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.wws.springjunior.annotation.AnnotationParse;
import cn.wws.springjunior.ioc.EnhanceMethod;
import cn.wws.springjunior.itf.SjInputOutputInterface;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;



public class CGLIBProxy implements MethodInterceptor {
    private Object target;
    public CGLIBProxy(Object target) throws ClassNotFoundException {
        this.target = target;
    }

    public <T> T getProxy() {
        return (T) Enhancer.create(this.target.getClass(), this);
    }
    
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        ProxyEntity proxyEntity = new ProxyEntity(proxy, this.target.getClass(), obj, method, args);
        doEnhanceMethod(proxyEntity, AnnotationParse.getSjBeforeMap());
        boolean need = isNeedPerformanceAnalysis(proxyEntity, method, AnnotationParse.getSjPerformanceAnalysisMap());
        long cut = 0;
        if (need) {
            cut = System.currentTimeMillis();
        }
        Object object = proxyEntity.getMethodProxy().invokeSuper(proxyEntity.getObject(), proxyEntity.getArgs());  // 调用方法
        if (need) {
            cut = System.currentTimeMillis() - cut;
            SjInputOutputInterface ioput = (SjInputOutputInterface) AnnotationParse.getSjSystemParam("defaultInputOutput");
            ioput.output(method.getName() + "耗时" + cut);
        }
        doEnhanceMethod(proxyEntity, AnnotationParse.getSjAfterMap());
        return object;
    }

    /*执行bofore或者after方法*/
    private void doEnhanceMethod(ProxyEntity proxyEntity, Map<String, EnhanceMethod> methodMap) throws Exception {
        String proxyMethodValue = proxyEntity.getMethod().toString().substring(
                proxyEntity.getMethod().toString().lastIndexOf(" ") + 1, 
                proxyEntity.getMethod().toString().indexOf("("));
        for (Map.Entry<String, EnhanceMethod> map : methodMap.entrySet()) {
            String methodName = getMethodName(map.getValue().getAnnotationValue());
            if (proxyMethodValue.startsWith(methodName)) {
                String toIntance = map.getKey();
                int offset = toIntance.lastIndexOf(".");
                String className = toIntance.substring(0, offset);
                Class<?> clazz = Class.forName(className, false, Thread.currentThread().getContextClassLoader()); // 加载该类
                String simpleName = toIntance.substring(offset + 1, toIntance.length());
                Method method = clazz.getDeclaredMethod(simpleName, proxyEntity.getArgs().getClass());
                method.invoke(clazz.newInstance(), (Object) proxyEntity.getArgs()); // 这一步需要原始的类
            }
        }
    }
    
    
    /**    
    * @Description: 获取方法名（去掉可能存在的括号）. 
    * @author songjun  
    * @date 2018年5月17日   
    * @param methodName
    * @return
    */ 
    private String getMethodName(String methodName) {
        String ret = null;
        if (StringUtils.isBlank(methodName)) {
            methodName = "";
        } else if (methodName.contains("(")) {
            methodName = methodName.substring(0, methodName.indexOf("("));
        } else {
            ret = methodName;
        }
        return methodName;
    }
    
    private boolean isNeedPerformanceAnalysis(ProxyEntity proxyEntity, Method method, Map<String, EnhanceMethod> methodMap) {
        String proxyMethodValue = proxyEntity.getMethod().toString().substring(
                proxyEntity.getMethod().toString().lastIndexOf(" ") + 1, 
                proxyEntity.getMethod().toString().indexOf("("));
        for (Map.Entry<String, EnhanceMethod> map : methodMap.entrySet()) {
            String methodName = getMethodName(map.getKey());
            if (proxyMethodValue.startsWith(methodName)) {
                return true;
            }
        }
        return false;
    }
}
