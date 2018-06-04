package cn.wws.springjunior.ioc;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingletonBeanCollection<T> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SingletonBeanCollection.class);
            
            
    private static SingletonBeanCollection instance = new SingletonBeanCollection();
    /**存放SJ创建的单例对象实例*/
    private HashMap<String, T> collection = new HashMap<String, T>();
    
    private  SingletonBeanCollection() { }
    
    public static SingletonBeanCollection getInstance() {
        return instance;
    }
    
    /**    
    * @Description: 存入单例对象. 
    * @author songjun  
    * @date 2018年6月4日   
    * @param className
    * @param bean
    */ 
    private void pushBean(String className, T bean) {
        collection.put(className, bean);
        LOGGER.debug("singletonBeanCollection={}", collection);
    }
    
    public T getBean(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        if (collection.get(className) == null) {
            Class<?> clazz = Class.forName(className);
            T obj = (T) clazz.newInstance();
            pushBean(className, obj);
            return obj;
        } else {
            return collection.get(className);
        }
    }
}
