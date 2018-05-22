package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;

@SjClass("duck")
public class Duck implements Animal {
    public void bark(String name) {
        System.out.println(name + "正在嘎嘎嘎");
    }
}
