package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjAfter;
import cn.wws.springjunior.annotation.SjAspect;
import cn.wws.springjunior.annotation.SjBefore;

@SjAspect
public class PersonAspect {
    @SjBefore("cn.wws.springjunior.test.entity.Person.getUp()")
    public void alermRing(Object[] args) {
        System.out.println(args[0] + "的闹钟响啦");
    }
    @SjAfter("cn.wws.springjunior.test.entity.Person")
    public void alermClosed(Object[] args) {
        System.out.println(args[0] + "已经搞定啦");
    }
}
