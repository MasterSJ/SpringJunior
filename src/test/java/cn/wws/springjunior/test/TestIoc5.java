package cn.wws.springjunior.test;

import cn.wws.springjunior.ioc.BeanFactory;
import cn.wws.springjunior.test.entity.Dog;

/**  
* @ClassName: TestIoc5  
* @Description: 测试ioc的单例与多例模式  
* @author songjun 
* @date 2018年6月8日  
*    
*/
public class TestIoc5 {
    public static void main(String[] args) {
        Dog dog = BeanFactory.getBean("dog");
        System.out.println("dog.p=" + dog.p);
        dog.bark();
        dog.p = 2;
        Dog dog2 = BeanFactory.getBean("dog");
        System.out.println("dog2.p=" + dog2.p);
    }
}
