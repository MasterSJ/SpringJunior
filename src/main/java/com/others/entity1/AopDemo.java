package com.others.entity1;

import cn.wws.springjunior.annotation.SjClass;
import cn.wws.springjunior.annotation.SjPointCut;

@SjClass("aopDemo")
public class AopDemo {
    @SjPointCut("preLogin")
    public void preHandleLogin() {
        System.out.println("这是preLogin，在标记方法之前执行------------测试aop");
    }
    
    @SjPointCut("afterLogin")
    public void afterHandleLogin() {
        System.out.println("这是afterLogin，在标记方法之后执行------------测试aop");
    }
}
