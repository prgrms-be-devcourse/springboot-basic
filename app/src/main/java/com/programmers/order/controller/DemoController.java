package com.programmers.order.controller;

import org.springframework.stereotype.Component;

import com.programmers.order.io.Output;
import com.programmers.order.message.ErrorMessage;
import com.programmers.order.type.ProgramType;

@Component
public class DemoController implements Controller {

	private final Output output;

	public DemoController(Output output) {
		this.output = output;
	}

	@Override
	public void run() {
		output.write(ErrorMessage.CLIENT_ERROR);
	}

	@Override
	public ProgramType getType() {
		return ProgramType.NONE;
	}
}
