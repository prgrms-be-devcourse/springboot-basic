package study.dev.spring.voucher.presentation.converter;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static study.dev.spring.voucher.exception.VoucherErrorCode.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.exception.VoucherException;

@DisplayName("[VoucherConverter Test] - Presentation Layer")
class VoucherConverterTest {

	private final VoucherConverter voucherConverter = new VoucherConverter();

	@Nested
	@DisplayName("[문자열 정보를 바우처 생성 요청 dto 로 컨버팅한다]")
	class convertToCreateRequestTest {

		@Test
		@DisplayName("[성공적으로 컨버팅한다]")
		void success() {
			//given
			final String data = "hello//1//1000";

			//when
			CreateVoucherRequest actual = voucherConverter.convertToCreateRequest(data);

			//then
			CreateVoucherRequest expected = new CreateVoucherRequest("hello", "FIXED", 1000);
			assertThat(actual).isEqualTo(expected);
		}

		@Test
		@DisplayName("[지원하지 않는 바우처 타입넘버 입력으로 실패한다]")
		void failByUnsupportedTypeNumber() {
			//given
			final String data = "hello//3//1000";

			//when
			ThrowingCallable when = () -> voucherConverter.convertToCreateRequest(data);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(VoucherException.class)
				.hasMessageContaining(UNSUPPORTED_TYPE_NUMBER.getMessage());
		}

		@Test
		@DisplayName("[잘못된 형식의 입력데이터로 실패한다]")
		void failByInvalidFormatData() {
			//given
			final String data = "hello//3//1000//hello";

			//when
			ThrowingCallable when = () -> voucherConverter.convertToCreateRequest(data);

			//then
			assertThatThrownBy(when)
				.isInstanceOf(VoucherException.class)
				.hasMessageContaining(INVALID_FORMAT_CREATE_REQUEST.getMessage());
		}
	}
}