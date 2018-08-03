package cn.wws.springjunior.test;

import cn.wws.springjunior.ioc.BeanFactory;
import cn.wws.springjunior.test.entity.Person;

/**  
* @ClassName: TestAop1  
* @Description: 测试aop
* @author songjun 
* @date 2018年5月18日  
*    
*/
public class TestAop1 {
	public static void main(String[] args) {
        Person person = BeanFactory.getBean("person");
        person.gotoSchool("小冰");
        person.getUp("小冰");
    }
}
