package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;

@SjClass(value="dog")
public class Dog {
    
    public void bark() {
        System.out.println("汪汪汪");
    }
}
