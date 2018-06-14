package cn.wws.springjunior.test;

import cn.wws.springjunior.ioc.BeanFactory;
import cn.wws.springjunior.test.entity.Tiger;

/**  
* @ClassName: TestIoc4  
* @Description: 增强类的祖先类也有增强属性需要注入的情况
* @author songjun 
* @date 2018年5月18日  
*    
*/
public class TestIoc4 {
    public static void main(String[] args) {
        Tiger tiger = BeanFactory.getBean("tiger");
        tiger.roar();
    }
}
