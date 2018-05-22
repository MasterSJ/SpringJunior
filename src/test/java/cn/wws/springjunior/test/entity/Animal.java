package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;

@SjClass("animal")
public interface Animal {
    public void bark(String name);
}
