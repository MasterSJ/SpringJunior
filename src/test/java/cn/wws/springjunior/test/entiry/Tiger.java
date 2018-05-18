package cn.wws.springjunior.test.entiry;

import cn.wws.springjunior.annotation.SjClass;

@SjClass("tiger")
public class Tiger extends Cat {
    public void bark() {
        System.out.println("哇呜");
        super.bark();
    }
}