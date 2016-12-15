package com.bizdata.commons;

import org.junit.Test;
import org.springframework.beans.BeanUtils;

public class BeanUtilsTest {

	@Test
	public void test() {
		TestEntity entity1 = new TestEntity();
		entity1.setId("1");
		entity1.setName("gjf");
		entity1.setAge(30);

		TestEntity entity2 = new TestEntity();
		entity2.setName("gjj");

		BeanUtils.copyProperties(entity2, entity1, new String[] {"id", "name" });

		System.out.println(entity1);
		System.out.println(entity2);
	}

}
