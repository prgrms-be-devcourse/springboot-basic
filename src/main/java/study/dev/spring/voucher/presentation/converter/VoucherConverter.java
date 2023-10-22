package study.dev.spring.voucher.presentation.converter;

import static study.dev.spring.voucher.exception.VoucherErrorCode.*;

import org.springframework.stereotype.Component;

import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.exception.VoucherException;

@Component
public class VoucherConverter {

	private static final String DELIMITER = "//";
	private static final String[] TYPE_LIST = {"FIXED", "PERCENT"};

	public CreateVoucherRequest convertToCreateRequest(final String data) {
		String[] splitData = data.split(DELIMITER);
		validateFormat(splitData);

		return new CreateVoucherRequest(
			splitData[0],
			getVoucherType(Integer.parseInt(splitData[1])),
			Double.parseDouble(splitData[2])
		);
	}

	private void validateFormat(String[] splitData) {
		if (splitData.length != 3) {
			throw new VoucherException(INVALID_FORMAT_CREATE_REQUEST);
		}
	}

	private String getVoucherType(int typeNumber) {
		try {
			return TYPE_LIST[typeNumber];
		} catch (IndexOutOfBoundsException e) {
			throw new VoucherException(UNSUPPORTED_TYPE_NUMBER);
		}
	}
}
