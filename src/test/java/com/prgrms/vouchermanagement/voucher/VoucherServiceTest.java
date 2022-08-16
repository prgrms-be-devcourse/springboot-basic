package com.prgrms.vouchermanagement.voucher;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prgrms.vouchermanagement.commons.exception.CreationFailException;
import com.prgrms.vouchermanagement.voucher.repository.VoucherRepository;

@ExtendWith(MockitoExtension.class)
@DisplayName("VoucherService 클래스 ")
public class VoucherServiceTest {
	@InjectMocks
	private VoucherService voucherService;

	@Mock
	private VoucherRepository voucherRepository;

	private VoucherType fixedType = VoucherType.FIXED;
	private VoucherType percentType = VoucherType.PERCENT;

	@Nested
	@DisplayName("create 메소드는")
	class Describe_create {
		long zeroAmount = 0L;

		@Nested
		@DisplayName("할인 정보가 100 보다 큰 양수라면")
		class Context_with_biggerThan100Amount {
			long biggerThan100Amount = 1000L;

			@Test
			@DisplayName(" 고정할인 금액 바우처를 정상 발급한다")
			void it_returns_a_valid_FixedVoucher() {
				Assertions.assertDoesNotThrow(() ->
					voucherService.create(fixedType, biggerThan100Amount));
			}

			@Test
			@DisplayName(" 퍼센트 할인 바우처 생성에 실패한다")
			void it_throws_creationException() {
				assertThatThrownBy(() ->
					voucherService.create(percentType, biggerThan100Amount))
					.isInstanceOf(CreationFailException.class);
			}
		}

		@Nested
		@DisplayName("할인 정보가 음수라면")
		class Context_with_negativeAmount {
			long negativeAmount = -10L;

			@Test
			@DisplayName(" 고정할인 금액 바우처 생성에 실패한다")
			void it_returns_a_valid_FixedVoucher() {
				assertThatThrownBy(() ->
					voucherService.create(fixedType, negativeAmount))
					.isInstanceOf(CreationFailException.class);
			}

			@Test
			@DisplayName("퍼센트 할인 바우처 생성에 실패한다")
			void it_throws_creationException() {
				assertThatThrownBy(() ->
					voucherService.create(percentType, negativeAmount))
					.isInstanceOf(CreationFailException.class);
			}
		}

		@Nested
		@DisplayName("할인 정보가 0 이라면")
		class Context_with_zeroAmount {
			long zeroAmount = 0L;

			@Test
			@DisplayName(" 고정할인 금액 바우처 생성에 실패한다")
			void it_throws_creationException_creatingFixed() {
				assertThatThrownBy(() ->
					voucherService.create(fixedType, zeroAmount))
					.isInstanceOf(CreationFailException.class);
			}

			@Test
			@DisplayName("퍼센트 할인 바우처 생성에 실패한다")
			void it_throws_creationException_creatingPercent() {
				assertThatThrownBy(() ->
					voucherService.create(percentType, zeroAmount))
					.isInstanceOf(CreationFailException.class);
			}
		}
	}
}