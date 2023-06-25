package co.programmers.voucher.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

@Component
public class Console implements InputView, OutputView {
	private static final String STARTER_MESSAGE =
			"=== Voucher Program ===\n"
					+ "Type exit to exit the program.\n"
					+ "Type create to create a new voucher.\n"
					+ "Type list to list all vouchers.";
	private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public Object input() throws IOException {
		return bufferedReader.readLine();
	}

	@Override
	public void printGuideMessage() {
		System.out.println(STARTER_MESSAGE);
	}

	@Override
	public void println(String content) {
		System.out.println(content);
	}

	@Override
	public void print(String content) {
		System.out.print(content);
	}
}
