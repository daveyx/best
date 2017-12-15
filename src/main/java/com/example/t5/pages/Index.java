package com.example.t5.pages;

import org.apache.tapestry5.annotations.SetupRender;
import org.apache.tapestry5.ioc.annotations.Inject;

import com.example.t5.services.IT5Service;

public class Index {

	@Inject
	private IT5Service someInterface;
	
	@SetupRender
	void setup()
	{
		someInterface.doSomething();
	}
}
