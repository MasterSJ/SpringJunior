package com.others.test;

import com.others.entity1.Feng;

import cn.wws.springjunior.ioc.BeanFactory;

/**  
* @ClassName: TestIoc  
* @Description: 测试ioc
* @author songjun 
* @date 2018年5月18日  
*    
*/
public class TestIoc{
    /**    
    * @Description: 测试入口. 
    * @author songjun  
    * @date 2018年4月11日   
    * @param args
    */ 
    
    public static void main(String[] args) {
        /**加载配置信息*/
        System.out.println("测试ioc");
        Feng feng = BeanFactory.getBean("feng");
        feng.test1();
        feng.test2();
    }
}
