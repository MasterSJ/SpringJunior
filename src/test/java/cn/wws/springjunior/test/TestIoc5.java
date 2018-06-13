package cn.wws.springjunior.test;

import cn.wws.springjunior.ioc.BeanFactory;
import cn.wws.springjunior.test.entity.Bird;

/**  
* @ClassName: TestIoc5  
* @Description: 测试ioc的单例与多例模式  
* @author songjun 
* @date 2018年6月8日  
*    
*/
public class TestIoc5 {
    public static void main(String[] args) {
        Bird bird = BeanFactory.getBean("bird");
        System.out.println("bird.number=" + bird.number);
        bird.bark();
        
        System.out.println("-----------------------------");
        
        bird.number = 2;
        Bird bird2 = BeanFactory.getBean("bird");
        System.out.println("bird2.number=" + bird2.number);
        bird2.bark();
    }
}
