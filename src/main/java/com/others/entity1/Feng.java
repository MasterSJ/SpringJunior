package com.others.entity1;

import cn.wws.springjunior.annotation.SjClass;
import cn.wws.springjunior.annotation.SjField;
import cn.wws.springjunior.annotation.SjMethod;

@SjClass("feng")
public class Feng {
    @SjField("defaultHandler")
    private AbstractHandler handler;
    
    @SjField
    private Dong abcddd;

    public void test1() {
        System.out.println("������Feng�ķ���test2�����ԡ�ͨ��ȱʡ��ʽ��ʵ������ͨ����");
        abcddd.meth1();
    }
    
    @SjMethod
    public void test2() {
        System.out.println("������Feng�ķ���test1�����ԡ�ͨ��ָ��ʵ������ʵ�����ӿڻ���������");
        handler.doHandle();
    }
}
