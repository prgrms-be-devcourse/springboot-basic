package com.example.voucher.domain.repository;

import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.repository.VoucherMemoryRepository;
import com.example.voucher.domain.voucher.repository.VoucherRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Map;

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

			@Test
			@DisplayName("바우처를 저장하고 저장된 바우처를 반환한다")
			void 바우처를_저장하고_저장된_바우처를_반환한다() {
				Voucher savedVoucher = voucherRepository.save(new FixedAmountVoucher(null, 1000));

				try {
					Map<Long, Voucher> map = (Map<Long, Voucher>) store.get(voucherRepository);
					Assertions.assertThat(map.get(savedVoucher.getVoucherId())).isEqualTo(savedVoucher);
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
	}

}
