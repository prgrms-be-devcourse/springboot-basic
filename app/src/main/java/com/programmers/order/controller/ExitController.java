package com.programmers.order.controller;

import org.springframework.stereotype.Component;

import com.programmers.order.io.Output;
import com.programmers.order.message.BasicMessage;
import com.programmers.order.type.ProgramType;

@Component
public class ExitController implements Controller {

	private final Output output;

	public ExitController(Output output) {
		this.output = output;
	}

	@Override
	public void run() {
		output.write(BasicMessage.CommonMessage.EXIT);
	}

	@Override
	public ProgramType getType() {
		return ProgramType.EXIT;
	}
}
