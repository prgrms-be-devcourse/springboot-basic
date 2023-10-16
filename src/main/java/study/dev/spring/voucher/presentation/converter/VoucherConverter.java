package study.dev.spring.voucher.presentation.converter;

import static study.dev.spring.voucher.exception.VoucherErrorCode.*;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.exception.VoucherException;

@Component
public class VoucherConverter {

	private static final String DELIMITER = "//";

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
			return VoucherTypeMather.values()[typeNumber - 1].getVoucherType();
		} catch (IndexOutOfBoundsException e) {
			throw new VoucherException(UNSUPPORTED_TYPE_NUMBER);
		}
	}

	@Getter
	@RequiredArgsConstructor
	private enum VoucherTypeMather {
		ONE("FIXED"),
		TWO("PERCENT");
		private final String voucherType;
	}
}
