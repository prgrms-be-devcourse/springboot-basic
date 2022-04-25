package com.example.voucher.io;
import com.example.voucher.dto.VoucherListResponse;
import org.springframework.stereotype.Component;


import static com.example.voucher.exception.ErrorMessage.UNSUPPORTED_MESSAGE_TYPE;

@Component
public class ConsoleWriter<T> implements Output<T> {

	private enum MessageType {
		STRING(String.class),
		VOUCHER_RESPONSE(VoucherListResponse.class);

		MessageType(Class<?> classType) {
			this.classType = classType;
		}

		Class<?> classType;
	}

	@Override
	public void printMessage(T message) {
		try {
			System.out.println(convert(message, message.getClass()));
		} catch (IllegalArgumentException e) {
			// TODO: 로그 남기기
		}
	}

	private String convert(T message, Class<?> classType) {
		if (classType == MessageType.STRING.classType) {
			return message.toString();
		}
		if (classType == MessageType.VOUCHER_RESPONSE.classType) {
			return message.toString();
		}
		throw new IllegalArgumentException(UNSUPPORTED_MESSAGE_TYPE.name());
	}
}
