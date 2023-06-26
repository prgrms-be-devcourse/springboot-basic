package co.programmers.voucher.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.List;

import org.springframework.stereotype.Component;

import co.programmers.voucher.dto.Response;
import co.programmers.voucher.dto.VoucherInquiryResponseDTO;

@Component
public class Console implements InputView, OutputView {
	private static final String STARTER_MESSAGE =
			"=== Voucher Program ===\n"
					+ "Type exit or 'x' to exit  the program.\n"
					+ "Type create or 'c' to create a new voucher.\n"
					+ "Type list or 'l' to list all vouchers.";
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
	public void println(Object content) {
		System.out.println(content);
	}

	@Override
	public void print(Object content) {
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
		if (!(responseData instanceof List)) {
			System.out.println(contents);
			return;
		}
		for (var content : (List)responseData) {
			if (content instanceof VoucherInquiryResponseDTO) {
				System.out.println(MessageFormat.format("id : {0}", ((VoucherInquiryResponseDTO)content).getId()));
				System.out.println(MessageFormat.format("name : {0}", ((VoucherInquiryResponseDTO)content).getName()));
				System.out.println(MessageFormat.format("description : {0}",
						((VoucherInquiryResponseDTO)content).getDescription()));
				System.out.println(MessageFormat.format("discountType : {0}",
						((VoucherInquiryResponseDTO)content).getDiscountType()));
				System.out.println(MessageFormat.format("discountAmount : {0}",
						((VoucherInquiryResponseDTO)content).getDiscountAmount()));
				System.out.println(
						MessageFormat.format("create At : {0}", ((VoucherInquiryResponseDTO)content).getCreatedAt()));
				System.out.println(
						MessageFormat.format("expire At : {0}", ((VoucherInquiryResponseDTO)content).getExpiredAt()));
			} else {
				System.out.println(content);
			}
		}

	}
}
