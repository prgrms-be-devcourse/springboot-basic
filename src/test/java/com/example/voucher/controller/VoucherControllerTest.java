package com.example.voucher.controller;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.service.voucher.VoucherService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

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

		@Test
		@DisplayName("바우처를 생성하고 저장한다")
		void 바우처를_생성하고_저장한다() {
			voucherController.save(FIXED_AMOUNT_VOUCHER, 1000);
			verify(voucherService).save(any(VoucherType.class), anyInt());
		}
	}
}
