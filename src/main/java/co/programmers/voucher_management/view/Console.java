package co.programmers.voucher_management.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import co.programmers.voucher_management.voucher.dto.Response;
import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;

@Component
public class Console implements InputView, OutputView {
	private static final String STARTER_MESSAGE =
			"=== Voucher Program ===\n"
					+ "Type exit or 'x' to exit  the program.\n"
					+ "Type create or 'c' to create a new voucher.\n"
					+ "Type list or 'l' to list all vouchers.";
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
			System.out.println(MessageFormat.format("id : {0}", ((VoucherResponseDTO)content).getId()));
			System.out.println(MessageFormat.format("discountType : {0}",
					((VoucherResponseDTO)content).getDiscountType()));
			System.out.println(MessageFormat.format("discountAmount : {0}",
					((VoucherResponseDTO)content).getDiscountAmount()));

		}
	}
}
