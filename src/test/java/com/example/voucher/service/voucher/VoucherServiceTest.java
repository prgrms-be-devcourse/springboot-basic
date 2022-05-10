package com.example.voucher.service.voucher;

import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;
import static com.example.voucher.domain.voucher.VoucherType.PERCENT_DISCOUNT_VOUCHER;
import static com.example.voucher.exception.ErrorMessage.INVALID_INPUT;
import static com.example.voucher.exception.ErrorMessage.VOUCHER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
						.hasMessage(INVALID_INPUT.getMessage());
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
						.hasMessage(INVALID_INPUT.getMessage());
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
						.hasMessage(INVALID_INPUT.getMessage());
			}
		}
	}
	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findAll메서드는 {

		private List<Voucher> list;

		@BeforeEach
		void 테스트를_위한_설정() {
			list = Arrays.asList(new FixedAmountVoucher(null, 1000));
			given(voucherRepository.findAll())
					.willReturn(list);
		}

		@Test
		@DisplayName("바우처를 전체 조회하고 반환한다")
		void 바우처를_전체_조회하고_반환한다() {
			List<Voucher> vouchers = voucherService.findAll();

			verify(voucherRepository).findAll();
			assertThat(vouchers).isEqualTo(list);
		}
	}


	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class deleteById메서드는 {

		@Test
		@DisplayName("바우처 아이디로 바우처를 삭제한다")
		void 바우처_아이디로_바우처를_삭제한다() {
			// given
			given(voucherRepository.deleteById(anyLong()))
					.willReturn(1);

			// when
			voucherService.deleteById(1L);

			// then
			verify(voucherRepository).deleteById(anyLong());
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 존재하지_않는_바우처_아이디라면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {

				given(voucherRepository.deleteById(anyLong()))
						.willReturn(0);

				assertThatThrownBy(() -> voucherService.deleteById(1L))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(VOUCHER_NOT_FOUND.getMessage());
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findById메서드는 {

		@Test
		@DisplayName("바우처 아이디로 바우처를 조회하고 반환한다")
		void 바우처_아이디로_바우처를_조회하고_반환한다() {

			// given
			Voucher createdVoucher = new FixedAmountVoucher(1L, 1000);
			given(voucherRepository.findById(anyLong()))
					.willReturn(Optional.of(createdVoucher));

			// when
			Voucher voucher = voucherService.findById(createdVoucher.getVoucherId());

			// then
			verify(voucherRepository).findById(anyLong());
			assertThat(voucher).isEqualTo(createdVoucher);
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 존재하지_않는_바우처_아이디라면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {

				given(voucherRepository.findById(anyLong()))
						.willReturn(Optional.empty());

				assertThatThrownBy(() -> voucherService.findById(1L))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(VOUCHER_NOT_FOUND.getMessage());
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findByCreatedAt메서드는 {

		@Test
		@DisplayName("바우처 생성기간으로 바우처를 조회하고 반환한다")
		void 바우처_생성기간으로_바우처를_조회하고_반환한다() {

			// given
			List<Voucher> createdVouchers = Arrays.asList(new FixedAmountVoucher(1L, 1000),
					new FixedAmountVoucher(2L, 2000));
			given(voucherRepository.findByCreatedAt(any(LocalDate.class)))
					.willReturn(createdVouchers);

			// when
			List<Voucher> vouchers = voucherService.findByCreatedAt(LocalDate.now());

			// then
			verify(voucherRepository).findByCreatedAt(any(LocalDate.class));
			assertThat(vouchers).isEqualTo(createdVouchers);
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findByVoucherType메서드는 {

		@Test
		@DisplayName("바우처 타입으로 바우처를 조회하고 반환한다")
		void 바우처_타입으로_바우처를_조회하고_반환한다() {

			// given
			List<Voucher> createdVouchers = Arrays.asList(new FixedAmountVoucher(1L, 1000),
					new FixedAmountVoucher(2L, 2000));
			given(voucherRepository.findByVoucherType(any(VoucherType.class)))
					.willReturn(createdVouchers);

			// when
			List<Voucher> vouchers = voucherService.findByVoucherType(FIXED_AMOUNT_VOUCHER);

			// then
			verify(voucherRepository).findByVoucherType(any(VoucherType.class));
			assertThat(vouchers).isEqualTo(createdVouchers);
		}
	}
}

