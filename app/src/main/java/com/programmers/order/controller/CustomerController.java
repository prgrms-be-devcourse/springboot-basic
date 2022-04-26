package com.programmers.order.controller;

import org.springframework.stereotype.Component;

import com.programmers.order.io.Input;
import com.programmers.order.io.Output;
import com.programmers.order.type.ProgramType;

@Component("Customer")
public class CustomerController implements Controller {
	private final Input input;
	private final Output output;

	public CustomerController(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		System.out.println("customer management!!");
	}

	@Override
	public ProgramType getType() {
		return ProgramType.CUSTOMER;
	}
}
