package com.uni.app.main.controller;

import javax.swing.text.AbstractDocument.Content;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:9786", maxAge = 3600)
@RestController
public abstract class AbstractController  implements ApplicationContextAware {
	protected static final Logger logger = LoggerFactory.getLogger(ComparatorController.class);
	protected ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@RequestMapping(value = "/helloworld")
	public String helloWorldMethod() {
		return "Hello World is ACTIVE";
	}
	
	@RequestMapping(value = "/shutdownContext")
	public void shutdownContext() {
		((ConfigurableApplicationContext) applicationContext).close();
	}
}
