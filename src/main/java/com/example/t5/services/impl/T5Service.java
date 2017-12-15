package com.example.t5.services.impl;

import org.apache.tapestry5.annotations.Service;
import org.springframework.web.servlet.ViewResolver;

import com.example.t5.services.IT5Service;

public class T5Service implements IT5Service {

//	@Inject
//	private UserAccountRepository repository;

    final ViewResolver springViewResolver;

    public T5Service(@Service("mvcViewResolver") ViewResolver springViewResolver) {
        this.springViewResolver = springViewResolver;
    }

    @Override
    public ViewResolver testMethod() {
        return springViewResolver;
    }

    @Override    
    public String doSomething() {
//		for (UserAccount customer : repository.findAll()) {
//			System.out.println(customer);
//		}
//
//		repository.deleteAll();
//
//		// save a couple of customers
//		repository.save(new UserAccount("Alice", "Smith"));
//		repository.save(new UserAccount("Bob", "Smith"));
//
//		// fetch all customers
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (UserAccount customer : repository.findAll()) {
//			System.out.println(customer);
//		}
//		System.out.println();
//
//		// fetch an individual customer
//		System.out.println("Customer found with findByFirstName('Alice'):");
//		System.out.println("--------------------------------");
//		System.out.println(repository.findByFirstName("Alice"));
//
//		System.out.println("Customers found with findByLastName('Smith'):");
//		System.out.println("--------------------------------");
//		for (UserAccount customer : repository.findByLastName("Smith")) {
//			System.out.println(customer);
//		}
//		
		return null;
    	
    }
}
