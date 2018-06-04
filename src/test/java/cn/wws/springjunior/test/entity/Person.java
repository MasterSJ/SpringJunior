package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;

@SjClass(value="person",isSingleton="false")
public class Person {
	public void getUp(String name) {
		System.out.println(name + "起床了");
	}
	
	public void gotoSchool(String name) {
	    System.out.println(name + "去上学");
	}
}
