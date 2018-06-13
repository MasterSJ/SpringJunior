package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;

@SjClass(value="bird",isSingleton="true")
public class Bird {
    
    public int number = 1;
    
    public void bark() {
        System.out.println("叽叽喳喳");
    }
}
