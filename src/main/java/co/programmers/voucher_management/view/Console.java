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
					+ "Type '1' or 'x' to exit the program.\n"
					+ "Type '2' to create a new voucher.\n"
					+ "Type '3' to list all vouchers.\n"
					+ "Type '4' to modify a voucher.\n"
					+ "Type '5' to delete a voucher.\n"
					+ "Type '6' to list customer blacklists.\n"
					+ "Type '7' to assign a voucher to the customer.\n"
					+ "Type '8' to list vouchers of certain customer.\n"
					+ "Type '9' to delete a voucher of a certain customer.\n"
					+ "Type '10' to find a customer of certain voucher.\n";
	private static final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

	@Override
	public Object input() {
		try {
			return bufferedReader.readLine();
		} catch (IOException ioException) {
			throw new RuntimeException();
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
	public void print(Response response) {
		if (response.getState() == Response.State.FAILED) {
			System.out.println(response.getResponseData());
			return;
		}
		if (response.getResponseData() == null) {
			return;
		}
		Object responseData = response.getResponseData();
		if (!(responseData instanceof List)) {
			System.out.println(responseData);
			return;
		}
		for (var content : (List)responseData) {
			System.out.println(content.toString());
		}
	}
}
