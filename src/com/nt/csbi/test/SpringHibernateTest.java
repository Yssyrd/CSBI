package com.nt.csbi.test;

import java.sql.SQLException;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ����
 *
 * @date: 2017��1��13�� ����4:07:40
 */
public class SpringHibernateTest {

	private ApplicationContext ctx = null;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void test() throws SQLException {
		
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}

}
