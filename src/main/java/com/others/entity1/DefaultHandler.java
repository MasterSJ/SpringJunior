package com.others.entity1;

import cn.wws.springjunior.annotation.SjClass;

@SjClass("defaultHandler")
public class DefaultHandler extends AbstractHandler {
    @Override
    public void doHandle() {
        super.doHandle();
        System.out.println("This is DefaultHandler.doHandle()");
    }
}
