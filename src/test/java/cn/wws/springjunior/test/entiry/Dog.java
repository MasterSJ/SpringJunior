package cn.wws.springjunior.test.entiry;

import cn.wws.springjunior.annotation.SjClass;

@SjClass("dog")
public class Dog {
    public void bark() {
        System.out.println("汪汪汪");
    }
}
