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
@SjAspect("LogAspect")
public class LogAspect {
    @SjBefore("com.others.entity1.Feng.test1()")
    public boolean before() {
        System.out.println("出门之前，洗脸刷牙");
        return false;
    }

    @SjAfter("com.others.entity1.Feng.test1()")
    public boolean after(Method method, Object[] args) {
        System.out.println("到达公司，开始上班");
        return false;
    }
    
}
