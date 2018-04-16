package cn.wws.springjunior;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.others.entity1.AbstractHandler;
import com.others.entity1.Feng;
import com.others.entity1.Handler;

import cn.wws.springjunior.annotation.AnnotationParse;
import cn.wws.springjunior.annotation.BeanFactory;

/**  
* @ClassName: SpringJuniorStarter  
* @Description: 整个项目启动器  
* @author songjun 
* @date 2018年4月11日  
*    
*/
public class SpringJuniorStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringJuniorStarter.class);
    static {
        init();
    }
    
    public static void init() {
        LOGGER.debug("加载执行启动器...");
        /**加载配置信息*/
        AnnotationParse.init("com.others.entity1");
    }
    
    /**    
    * @Description: 测试入口. 
    * @author songjun  
    * @date 2018年4月11日   
    * @param args
    */ 
    
    public static void main(String[] args) {
        Feng feng = BeanFactory.getBean("feng");
        feng.test1();
        feng.test2();
    }
}
