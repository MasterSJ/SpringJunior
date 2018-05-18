package com.others.test;

import com.others.entity1.BeanOfAopTest;

import cn.wws.springjunior.ioc.BeanFactory;

/**  
* @ClassName: TestAop  
* @Description: 测试aop
* @author songjun 
* @date 2018年5月18日  
*    
*/
public class TestAop {
    public static void main(String[] args) throws ClassNotFoundException {
        /**加载配置信息*/
        System.out.println("测试aop");
        /*aop增强标记类是com.others.entity1.LogAspect*/
        BeanOfAopTest ins = BeanFactory.getBean("testAopBean");
        ins.sing("小红", "小明");
        
        
    }
    
}
