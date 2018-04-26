package com.others.entity1;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.wws.springjunior.annotation.SjActiveAspect;
import cn.wws.springjunior.aop.AbstractCglibInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**  
* @ClassName: LogAspect  
* @Description: 日志切面  
* @author songjun 
* @date 2018年4月25日  
*/
@SjActiveAspect("LogAspect")
public class LogAspect extends AbstractCglibInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCglibInterceptor.class);
    @Override
    public boolean before(Method method, Object[] args) {
        LOGGER.info("------------统一打印日志，入参={}", args);
        return false;
    }

    @Override
    public boolean after(Method method, Object[] args) {
        LOGGER.info("------------统一打印日志，返回值={}", args);
        return false;
    }
    
    @Override   /**此方法不是必须要实现覆盖的*/
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        boolean b = before(method, args);    
        Object object = proxy.invokeSuper(obj, args);  
        Object[] objs = new Object[1];
        objs[0] = object;
        boolean a = after(method, objs);   
        return object;  
    }
}
