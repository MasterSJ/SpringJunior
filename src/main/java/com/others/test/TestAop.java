package com.others.test;

import com.others.entity1.BeanOfAopTest;
import com.others.entity1.Feng;

import cn.wws.springjunior.SpringJuniorStarter;
import cn.wws.springjunior.aop.CGLIBProxy;
import cn.wws.springjunior.ioc.BeanFactory;

public class TestAop {
    public static void main(String[] args) throws ClassNotFoundException {
        /**加载配置信息*/
        SpringJuniorStarter.init("com.others");
        System.out.println("测试aop");
        BeanOfAopTest ins = BeanFactory.getBean("testAopBean");
        ins.sing("小红", "小明");
        
        
    }
    
}
