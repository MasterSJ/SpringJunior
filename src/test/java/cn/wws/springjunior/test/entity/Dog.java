package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;

@SjClass("dog")
public class Dog {
    public int p = 1;
    
    public void bark() {
        System.out.println("汪汪汪");
    }
}
