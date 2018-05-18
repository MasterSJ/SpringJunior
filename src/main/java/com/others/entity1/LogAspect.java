package com.others.entity1;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.wws.springjunior.annotation.SjAfter;
import cn.wws.springjunior.annotation.SjAspect;
import cn.wws.springjunior.annotation.SjBefore;

/**  
* @ClassName: LogAspect  
* @Description: 日志切面  
* @author songjun 
* @date 2018年4月25日  
*/
@SjAspect
public class LogAspect {
    @SjBefore("com.others.entity1.BeanOfAopTest.sing")
    public void before(Object[] args) {
        System.out.println(args[0] + "和" + args[1] + "唱歌之前，先清清嗓子");
    }

    @SjAfter("com.others.entity1.BeanOfAopTest.sing()")
    public void after(Object[] args) {
        System.out.println(args[0] + "和" + args[1] + "唱完啦");
    }
    
}
