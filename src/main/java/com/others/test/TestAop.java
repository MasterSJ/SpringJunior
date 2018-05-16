package com.others.test;

import com.others.entity1.Feng;

import cn.wws.springjunior.SpringJuniorStarter;
import cn.wws.springjunior.ioc.BeanFactory;

public class TestAop {
    public static void main(String[] args) {
        /**加载配置信息*/
        //SpringJuniorStarter.init("com.others");
        System.out.println("aop调用：");
        Feng f1 = BeanFactory.getBean("feng");
        f1.test1();
    }
    
}
