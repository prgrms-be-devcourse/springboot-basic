package com.example.voucher.service.voucher;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("VoucherService 클래스의")
public class VoucherServiceTest {
	@InjectMocks
	private VoucherServiceImpl voucherService;

	@Mock
	private VoucherRepository voucherRepository;

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Test
		@DisplayName("바우처를 생성하고 저장한 다음 생성된 바우처를 반환한다")
		void 바우처를_생성하고_저장한_다음_생성된_바우처를_반환한다() {
			voucherService.save(FIXED_AMOUNT_VOUCHER, 1000);
			verify(voucherRepository).save(any(Voucher.class));
		}
	}
}
