package cn.wws.springjunior.test;

import cn.wws.springjunior.ioc.BeanFactory;
import cn.wws.springjunior.test.entity.Dog;


/**  
* @ClassName: TestIoc1  
* @Description: 普通ioc
* @author songjun 
* @date 2018年5月18日  
*    
*/
public class TestIoc1 {
    
    public static void main(String[] args) {
        Dog dog = BeanFactory.getBean("dog");
        System.out.println("dog.p=" + dog.p);
        dog.bark();
        dog.p = 2;
        Dog dog2 = BeanFactory.getBean("dog");
        System.out.println("dog2.p=" + dog2.p);
    }
}
