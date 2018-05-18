package cn.wws.springjunior.test.entiry;

import cn.wws.springjunior.annotation.SjClass;

@SjClass("duck")
public class Duck implements Animal {
    public void bark() {
        System.out.println("嘎嘎嘎");
    }
}
