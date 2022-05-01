package com.example.voucher.domain.repository;

import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.domain.voucher.repository.VoucherJdbcRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import static com.example.voucher.exception.ErrorMessage.SERVER_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@ContextConfiguration(classes = VoucherJdbcRepositoryTest.Config.class)
public class VoucherJdbcRepositoryTest {

	@Configuration
	@EnableAutoConfiguration
	static class Config {

		@Bean
		public VoucherJdbcRepository voucherJdbcRepository(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			return new VoucherJdbcRepository(dataSource, namedParameterJdbcTemplate);
		}

	}

	@Autowired
	private VoucherJdbcRepository voucherJdbcRepository;

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Test
		@DisplayName("바우처를 저장하고 저장된 바우처를 반환한다")
		void 바우처를_저장하고_저장된_바우처를_반환한다() {
			Voucher savedVoucher = voucherJdbcRepository.save(new FixedAmountVoucher(null, 1000));

			List<Voucher> findVouchers = voucherJdbcRepository.findAll();
			assertThat(findVouchers.size()).isEqualTo(1);
			assertThat(savedVoucher).isEqualTo(findVouchers.get(0));
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 바우처가_null이_넘어온다면 {

			@Test
			@DisplayName("IllegalArgumentException 예외를 던진다")
			void IllegalArgumentException_예외를_던진다() {
				assertThatThrownBy(() -> voucherJdbcRepository.save(null))
						.isInstanceOf(IllegalArgumentException.class)
						.hasMessage(SERVER_ERROR.name());
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findAll메서드는 {

		private List<Voucher> createdVouchers;

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 저장된_바우처가_없다면 {

			@Test
			@DisplayName("빈 리스트를 반환한다")
			void 빈_리스트를_반환한다() {
				List<Voucher> findVouchers = voucherJdbcRepository.findAll();
				assertThat(findVouchers.size()).isEqualTo(0);
			}
		}

		@Test
		@DisplayName("바우처를 전체 조회하고 조회된 바우처를 반환한다")
		void 바우처를_전체_조회하고_조회된_바우처를_반환한다() {

			//given
			FixedAmountVoucher voucher1 = new FixedAmountVoucher(1L, 10000);
			FixedAmountVoucher voucher2 = new FixedAmountVoucher(2L, 20000);
			voucherJdbcRepository.save(voucher1);
			voucherJdbcRepository.save(voucher2);
			createdVouchers = Arrays.asList(voucher1, voucher2);

			//when
			List<Voucher> findVouchers = voucherJdbcRepository.findAll();

			//then
			assertThat(findVouchers).isEqualTo(createdVouchers);
		}
	}
}

