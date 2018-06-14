package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;
import cn.wws.springjunior.annotation.SjField;

@SjClass("chook")
public class Chook {
    
    @SjField("duck")
    Animal animal;
    
    public void bark() {
        System.out.println("咕咕咕");
        animal.bark("唐老鸭");
    }
}
