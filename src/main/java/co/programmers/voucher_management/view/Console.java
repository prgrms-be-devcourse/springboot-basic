package co.programmers.voucher_management.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.stereotype.Component;

import co.programmers.voucher_management.common.Response;

@Component
public class Console implements InputView, OutputView {
	private static final String STARTER_MESSAGE =
			"=== Voucher Program ===\n"
					+ "Type exit or 'x' to exit the program.\n"
					+ "Type create or 'c' to create a new voucher.\n"
					+ "Type list or 'l' to list all vouchers.\n"
					+ "Type blacklist or 'b' to list customer blackLists.\n"
					+ "Type update or 'u' to modify a voucher.\n"
					+ "Type delete or 'd' to delete a voucher.\n";
	private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public Object input() {
		try {
			return bufferedReader.readLine();
		} catch (IOException ioException) {
			throw new RuntimeException("BufferedReader Failed");
		}
	}

	@Override
	public void printGuideMessage() {
		System.out.println(STARTER_MESSAGE);
	}

	@Override
	public void println(Object content) {
		System.out.println(content);
	}

	@Override
	public void print(Object content) {
		if (content instanceof List) {
			for (var c : (List)content) {
				print(c);
			}
			return;
		}
		System.out.print(content);
	}

	@Override
	public void print(Response contents) {
		if (contents.getState() == Response.State.FAILED) {
			System.out.println(contents.getResponseData());
			return;
		}
		if (contents.getResponseData() == null) {
			return;
		}
		Object responseData = contents.getResponseData();
		for (var content : (List)responseData) {
			System.out.println(content.toString());
		}
	}
}
