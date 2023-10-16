package study.dev.spring.voucher.presentation;

import static study.dev.spring.voucher.presentation.constants.Message.*;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import study.dev.spring.common.io.InputHandler;
import study.dev.spring.common.io.OutputHandler;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;
import study.dev.spring.voucher.presentation.converter.VoucherConverter;

@Component
@RequiredArgsConstructor
public class VoucherIoProcessor {

	private static final String DELIMITER = "//";
	private static final String NEW_LINE = System.lineSeparator();

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

	public void outputSuccessCreateMessage() {
		outputHandler.showSystemMessage("바우처를 성공적으로 생성되었습니다!");
	}

	public void outputVoucherInfo(List<VoucherInfo> voucherInfos) {
		StringBuilder sb = new StringBuilder();
		voucherInfos
			.forEach(voucherInfo -> {
				sb.append("이름 : ").append(voucherInfo.name()).append(NEW_LINE);
				sb.append("할인 타입 : ").append(voucherInfo.voucherType()).append(NEW_LINE);
				sb.append("할인가(률) : ").append(voucherInfo.discountAmount()).append(NEW_LINE);
			});

		outputHandler.showSystemMessage(sb.toString());
	}
}
