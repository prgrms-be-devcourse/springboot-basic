package co.programmers.voucher_management.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Console implements InputView, OutputView {
	private static final String STARTER_MESSAGE =
			"=== Voucher Program ===\n"
					+ "Type '0' or 'x' to exit the program.\n"
					+ "Type '1' to execute 'Voucher menu'.\n"
					+ "Type '2' to execute 'Customer menu'.\n";
	private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public Object input() {
		try {
			return bufferedReader.readLine();
		} catch (IOException ioException) {
			throw new RuntimeException("reading of console input failed");
		}
	}

	@Override
	public void printGuideMessage() {
		System.out.println(STARTER_MESSAGE);
	}

	@Override
	public void print(Object content) {
		if (content instanceof List) {
			for (var c : (List)content) {
				print(c + "\n");
			}
			return;
		}
		System.out.println(content);
	}
}
