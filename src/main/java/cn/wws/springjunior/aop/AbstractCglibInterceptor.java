package cn.wws.springjunior.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public abstract class AbstractCglibInterceptor implements MethodInterceptor {
    
    public abstract boolean before(Method method, Object[] args);
    
    public abstract boolean after(Method method, Object[] args);
    
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        boolean b = before(method, args);    
        Object object = proxy.invokeSuper(obj, args);  
        boolean a = after(method, args);   
        return object;  
    }
}
