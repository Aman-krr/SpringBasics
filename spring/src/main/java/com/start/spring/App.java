package com.start.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import dao.CustomerDao;
import dto.Customer;


public class App 
{
    public static void main( String[] args )
    {
    	ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
    	CustomerDao doimpl = (CustomerDao)context.getBean("custDAO");
    	List<Customer> custList = new ArrayList<>();
    	for(int i=0;i<10;i++) {
    	Customer c= new Customer();
    	c.setCust_id(107+i);
    	c.setCust_name("new User " +i);
    	c.setCity("bgp "+i);
    	c.setCust_add("new bgp "+i);
    	c.setState("bihar "+i);
    	c.setZipCode("0292 "+i);
    	custList.add(c);
    	}
    	//doimpl.create(c);
        //System.out.println(doimpl.update(c));
    	//System.out.println(doimpl.delete(c));
    	//System.out.println(doimpl.getInfo("103").toString());
    	
    	//System.out.println(doimpl.getInfoAll().toString());
System.out.println(doimpl.batchUpdate(custList));
    }
   
}
