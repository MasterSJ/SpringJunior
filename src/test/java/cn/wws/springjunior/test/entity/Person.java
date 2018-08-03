package cn.wws.springjunior.test.entity;

import cn.wws.springjunior.annotation.SjClass;
import cn.wws.springjunior.annotation.SjPerformanceAnalysis;

@SjClass(value="person",isSingleton="false")
public class Person {
	public void getUp(String name) {
		System.out.println(name + "起床了");
	}
	
	@SjPerformanceAnalysis
	public void gotoSchool(String name) {
	    System.out.println(name + "去上学");
	}
}
