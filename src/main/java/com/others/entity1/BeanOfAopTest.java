package com.others.entity1;

import cn.wws.springjunior.annotation.SjClass;
import cn.wws.springjunior.annotation.SjField;

@SjClass("testAopBean")
public class BeanOfAopTest {
    @SjField("defaultHandler")
    private AbstractHandler handler;
    
    public void sing(String name1, String name2) {
        System.out.println(name1 + "和" + name2 + "正在唱歌");
        handler.doHandle();
    }
}
