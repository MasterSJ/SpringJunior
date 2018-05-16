package com.others.entity1;

import cn.wws.springjunior.annotation.SjAfter;
import cn.wws.springjunior.annotation.SjBefore;
import cn.wws.springjunior.annotation.SjClass;
import cn.wws.springjunior.annotation.SjField;

@SjClass("feng")
public class Feng {
    @SjField("defaultHandler")
    private AbstractHandler handler;
    
    @SjField
    private Dong abcddd;

    public void test1() {
        System.out.println("这是类Feng的方法test1，测试“通过缺省方式，实例化普通对象”");
        abcddd.meth1();
    }
    
    public void test2() {
        System.out.println("这是类Feng的方法test2，测试“通过指定实现类来实例化接口或抽象类对象”");
        handler.doHandle();
    }
}
