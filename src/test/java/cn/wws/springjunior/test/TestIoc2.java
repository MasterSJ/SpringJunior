package cn.wws.springjunior.test;

import cn.wws.springjunior.ioc.BeanFactory;
import cn.wws.springjunior.test.entiry.Cat;

/**  
* @ClassName: TestIoc2  
* @Description: 测试ioc自动注入属性
* @author songjun 
* @date 2018年5月18日  
*    
*/
public class TestIoc2 {
    public static void main(String[] args) {
        Cat cat = BeanFactory.getBean("cat");
        cat.bark();
    }
}
