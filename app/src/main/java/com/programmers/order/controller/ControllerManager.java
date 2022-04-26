package com.programmers.order.controller;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ControllerManager {
	private Map<String, Controller> controllers;

	public ControllerManager(Map<String, Controller> controllers) {
		this.controllers = controllers;
	}

	public Controller of(String beanName) {
		return controllers.get(beanName);
	}

}
