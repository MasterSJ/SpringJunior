package com.others.entity1;

import cn.wws.springjunior.annotation.SjClass;

@SjClass("abstractHandler")
public abstract class AbstractHandler implements Handler {

    @Override
    public void doHandle() {
        System.out.println("This is AbstractHandler.doHandle()");
    }

}
