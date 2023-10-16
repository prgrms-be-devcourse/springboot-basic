package study.dev.spring.common.io;

import java.io.PrintStream;

import org.springframework.stereotype.Component;

@Component
public class ConsoleOutputHandler implements OutputHandler {

	private static final PrintStream out = System.out;
	private static final String NEW_LINE = System.lineSeparator();

	@Override
	public void showSystemMessage(final String message) {
		out.println(message);
	}

	@Override
	public void showStartMessage() {
		String message = "=== Voucher Program ===" + NEW_LINE
			+ "Type exit to exit the program." + NEW_LINE
			+ "Type create to create a new voucher." + NEW_LINE
			+ "Type list to list all vouchers." + NEW_LINE
			+ "Type black_list to list all black_list of customer." + NEW_LINE;

		out.println(message);
	}
}
