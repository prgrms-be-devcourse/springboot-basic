package com.programmers.springbasic.command.main;

import org.springframework.stereotype.Component;

import com.programmers.springbasic.command.Command;

@Component
public class ExitCommand implements Command {
	@Override
	public void execute() {
		System.exit(0);
	}

}
