package com.programmers.voucher.console;

import static com.programmers.voucher.console.run.Message.*;

import org.springframework.stereotype.Controller;

import com.programmers.voucher.console.io.Input;
import com.programmers.voucher.console.io.Output;
import com.programmers.voucher.console.run.AppPower;
import com.programmers.voucher.console.run.Command;
import com.programmers.voucher.console.run.CommandProcessor;

@Controller
public class ConsoleVoucherApp implements Runnable {

	private final Input input;
	private final Output output;
	private final CommandProcessor commandProcess;

	public ConsoleVoucherApp(Input input, Output output, CommandProcessor commandProcess) {
		this.input = input;
		this.output = output;
		this.commandProcess = commandProcess;
	}

	@Override
	public void run() {
		while (AppPower.isRunning()) {
			try {
				output.write(COMMAND_OPTION.getMessage());
				Command chosenCommand = Command.getCommand(input.read());
				commandProcess.doCommand(input, output, chosenCommand);
			} catch (RuntimeException e) {
				output.write(e.getMessage());
			}
		}
	}
}
