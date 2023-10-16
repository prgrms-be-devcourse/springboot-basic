package study.dev.spring.voucher.presentation;

import static study.dev.spring.voucher.presentation.constants.Message.*;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.presentation.converter.VoucherConverter;

@Component
@RequiredArgsConstructor
public class VoucherIoProcessor {

	private static final String DELIMITER = "//";

	private final InputHandler inputHandler;
	private final OutputHandler outputHandler;
	private final VoucherConverter converter;

	public CreateVoucherRequest inputCreateRequest() {
		StringBuilder sb = new StringBuilder();

		outputHandler.showSystemMessage(INPUT_NAME.getValue());
		String name = inputHandler.inputString();
		sb.append(name).append(DELIMITER);

		outputHandler.showSystemMessage(SELECT_VOUCHER_TYPE.getValue());
		int typeNumber = inputHandler.inputNumber();
		sb.append(typeNumber).append(DELIMITER);

		outputHandler.showSystemMessage(INPUT_DISCOUNT_AMOUNT.getValue());
		int discountAmount = inputHandler.inputNumber();
		sb.append(discountAmount);

		return converter.convertToCreateRequest(sb.toString());
	}
}
