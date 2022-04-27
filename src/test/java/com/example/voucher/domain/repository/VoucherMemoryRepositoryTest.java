package com.example.voucher.domain.repository;

import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.domain.voucher.repository.VoucherMemoryRepository;
import com.example.voucher.domain.voucher.repository.VoucherRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static com.example.voucher.domain.voucher.VoucherType.EMPTY;
import static com.example.voucher.exception.ErrorMessage.SERVER_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@DisplayName("VoucherMemoryRepository 클래스의")
public class VoucherMemoryRepositoryTest {

	private final VoucherRepository voucherRepository = new VoucherMemoryRepository();

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {
		private Field store;

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처가_넘어온다면 {

			@BeforeEach
			void 테스트_위한_설정() {
				try {
					store = voucherRepository.getClass().getDeclaredField("store");
					store.setAccessible(true);
				} catch (NoSuchFieldException e) {
					throw new RuntimeException(e.getMessage());
				}
			}

			@AfterEach
			void store_초기화_설정() {
				voucherRepository.deleteAll();
			}

			@Test
			@DisplayName("바우처를 저장하고 저장된 바우처를 반환한다")
			void 바우처를_저장하고_저장된_바우처를_반환한다() {
				Voucher savedVoucher = voucherRepository.save(new FixedAmountVoucher(null, 1000));

				try {
					Map<Long, Voucher> map = (Map<Long, Voucher>) store.get(voucherRepository);
					assertThat(map.get(savedVoucher.getVoucherId())).isEqualTo(savedVoucher);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처가_null이_넘어온다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherRepository.save(null))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(SERVER_ERROR.name());

			}
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처_타입이_EMPTY라면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {

				try (MockedStatic<VoucherType> voucherType = Mockito.mockStatic(VoucherType.class)) {
					voucherType.when(() -> VoucherType.of(anyString()))
							.thenReturn(EMPTY);

					assertThatThrownBy(() -> voucherRepository.save(new FixedAmountVoucher(null, 1000)))
							.isInstanceOf(IllegalArgumentException.class)
							.hasMessage(SERVER_ERROR.name());
				}
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findAll메서드는 {
		private Voucher createdVoucher;

		@AfterEach
		void 저장소_초기화() {
			voucherRepository.deleteAll();
		}

		@Test
		@DisplayName("바우처를 전체 조회하고 반환한다")
		void 바우처를_전체_조회하고_반환한다() {
			createdVoucher = voucherRepository.save(new FixedAmountVoucher(null, 1000));

			List<Voucher> vouchers = voucherRepository.findAll();

			assertThat(vouchers.size()).isEqualTo(1);
			assertThat(vouchers.get(0)).isEqualTo(createdVoucher);
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 저장된_바우처가_하나도_없다면 {

			@Test
			@DisplayName("빈 리스트를 반환한다")
			void 빈_리스트를_반환한다() {
				List<Voucher> vouchers = voucherRepository.findAll();

				assertThat(vouchers.size()).isEqualTo(0);
			}
		}
	}
}
