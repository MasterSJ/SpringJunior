package com.others.entity1;

import cn.wws.springjunior.annotation.SjClass;
import cn.wws.springjunior.annotation.SjField;
import cn.wws.springjunior.annotation.SjMethod;

@SjClass("dong")
public class Dong {
    @SjField
    private String d1;
    
    @SjMethod
    public void meth1() {
        System.out.println("这是类Dong的方法meth1");
    }
}
