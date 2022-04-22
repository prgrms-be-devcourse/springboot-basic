package com.example.voucher.controller;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.service.voucher.VoucherService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("VoucherController 클래스의")
public class VoucherControllerTest {

	@InjectMocks
	private VoucherController voucherController;

	@Mock
	private VoucherService voucherService;

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 지원하는_바우처_타입과_정수_타입의_할인_값이_넘어온다면 {

			@Test
			@DisplayName("바우처를 생성하고 저장한다")
			void 바우처를_생성하고_저장한다() {
				voucherController.save(FIXED_AMOUNT_VOUCHER, 1000);
				verify(voucherService).save(any(VoucherType.class), anyInt());
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_타입이_NULL이_넘어온다면 {

			@Test
			@DisplayName("처리하지 않고 리턴한다")
			void 처리하지_않고_리턴한다() {
				voucherController.save(null, 10000);
				verify(voucherService, never()).save(any(VoucherType.class), anyInt());
			}
		}
	}
}
