package com.example.voucher.service;

import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.repository.VoucherRepository;
import com.example.voucher.service.voucher.VoucherServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;
import static com.example.voucher.domain.voucher.VoucherType.PERCENT_DISCOUNT_VOUCHER;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_타입과_유효한_할인_값이_넘어온다면 {
			private Voucher createdVoucher;

			@BeforeEach
			void VoucherRepository_save메서드_반환_값_설정() {
				createdVoucher = new FixedAmountVoucher(null, 1000);
				given(voucherRepository.save(any(Voucher.class)))
						.willReturn(createdVoucher);
			}

			@Test
			@DisplayName("바우처를 생성하고 저장한 다음 생성된 바우처를 반환한다")
			void 바우처를_생성하고_저장한_다음_생성된_바우처를_반환한다() {
				Voucher savedVoucher = voucherService.save(FIXED_AMOUNT_VOUCHER, 1000);
				verify(voucherRepository).save(any(Voucher.class));
				assertThat(savedVoucher).isEqualTo(createdVoucher);
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class FIXED_AMOUNT_VOUCHER_바우처_타입과_음수인_할인_값이_넘어온다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherService.save(FIXED_AMOUNT_VOUCHER, -1000))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.name());
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class PERCENT_DISCOUNT_VOUCHER_바우처_타입과_음수인_할인_값이_넘어온다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherService.save(PERCENT_DISCOUNT_VOUCHER, -10))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.name());
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class PERCENT_DISCOUNT_VOUCHER_바우처_타입과_백을_넘는_할인_값이_넘어온다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherService.save(PERCENT_DISCOUNT_VOUCHER, 101))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(INVALID_INPUT.name());
			}
		}
	}
}
