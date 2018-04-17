package com.others.test;

import com.others.entity1.Feng;

import cn.wws.springjunior.SpringJuniorStarter;
import cn.wws.springjunior.annotation.AnnotationParse;
import cn.wws.springjunior.annotation.BeanFactory;

public class TestIoc {
    /**    
    * @Description: 测试入口. 
    * @author songjun  
    * @date 2018年4月11日   
    * @param args
    */ 
    
    public static void main(String[] args) {
        /**加载配置信息*/
        SpringJuniorStarter.init("com.others.entity1");
        
        
        Feng feng = BeanFactory.getBean("feng");
        feng.test1();
        feng.test2();
    }
}