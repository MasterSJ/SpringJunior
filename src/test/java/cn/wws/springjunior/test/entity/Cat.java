package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;
import cn.wws.springjunior.annotation.SjField;

@SjClass("cat")
public class Cat {
    @SjField
    Dog dog;
    
    public void bark() {
        dog.bark();
        System.out.println("喵喵喵");
    }
}
