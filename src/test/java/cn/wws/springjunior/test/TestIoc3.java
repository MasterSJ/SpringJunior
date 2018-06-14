package cn.wws.springjunior.test;

import cn.wws.springjunior.ioc.BeanFactory;
import cn.wws.springjunior.test.entity.Animal;
import cn.wws.springjunior.test.entity.Chook;

/**  
* @ClassName: TestIoc3  
* @Description: 接口或抽象类的实例化
* @author songjun 
* @date 2018年5月18日  
*    
*/
public class TestIoc3 {
    public static void main(String[] args) {
        Chook chook = BeanFactory.getBean("chook");
        chook.bark();
    }
}
