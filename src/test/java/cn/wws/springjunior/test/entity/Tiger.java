package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;

@SjClass("tiger")
public class Tiger extends Cat {
    public void bark() {
        super.bark();
        System.out.println("哇呜");
    }
}
