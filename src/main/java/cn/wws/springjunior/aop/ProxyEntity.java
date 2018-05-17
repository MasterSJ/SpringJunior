package cn.wws.springjunior.aop;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodProxy;

public class ProxyEntity {
    private final MethodProxy methodProxy;
    private final Class<?> clazz;
    private final Object object;
    private final Method method;
    private final Object[] args;

    public Object getObject() {
        return object;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Method getMethod() {
        return method;
    }

    public ProxyEntity(MethodProxy methodProxy, Class<?> clazz, Object object, Method method, Object[] args) {
        this.methodProxy = methodProxy;
        this.clazz = clazz;
        this.object = object;
        this.method = method;
        this.args = args;
    }

    public MethodProxy getMethodProxy() {
        return methodProxy;
    }
}
