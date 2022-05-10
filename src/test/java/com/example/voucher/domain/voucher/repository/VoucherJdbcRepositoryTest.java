package com.example.voucher.domain.voucher.repository;

import com.example.voucher.domain.voucher.FixedAmountVoucher;
import com.example.voucher.domain.voucher.Voucher;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static com.example.voucher.domain.voucher.VoucherType.FIXED_AMOUNT_VOUCHER;
import static com.example.voucher.exception.ErrorMessage.SERVER_ERROR;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
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
	@Order(1) // findAll전에 save 먼저 테스트
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class save메서드는 {

		@Test
		@DisplayName("바우처를 저장하고 저장된 바우처를 반환한다")
		void 바우처를_저장하고_저장된_바우처를_반환한다() {
			// given
			Voucher createdVoucher = new FixedAmountVoucher(null, 1000);

			// when
			Voucher savedVoucher = voucherJdbcRepository.save(createdVoucher);

			assertThat(savedVoucher.getVoucherType()).isEqualTo(createdVoucher.getVoucherType());
			assertThat(savedVoucher.getCreatedAt()).isEqualTo(createdVoucher.getCreatedAt());
			assertThat(savedVoucher.getDiscountAmount()).isEqualTo(createdVoucher.getDiscountAmount());
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
			Voucher voucher1 = voucherJdbcRepository.save(new FixedAmountVoucher(null, 10000));
			Voucher voucher2 = voucherJdbcRepository.save(new FixedAmountVoucher(null, 20000));
			List<Voucher> createdVouchers = Arrays.asList(voucher1, voucher2);

			//when
			List<Voucher> findVouchers = voucherJdbcRepository.findAll();

			//then
			assertThat(findVouchers).isEqualTo(createdVouchers);
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findById메서드는 {

		@Test
		@DisplayName("바우처 아이디로 바우처를 조회하고 조회된 바우처를 반환한다")
		void 바우처_아이디로_바우처를_조회하고_조회된_바우처를_반환한다() {

			// given
			Voucher createdVoucher = voucherJdbcRepository.save(new FixedAmountVoucher(null, 10000));

			// when
			Optional<Voucher> voucher = voucherJdbcRepository.findById(createdVoucher.getVoucherId());

			// then
			assertThat(voucher.isPresent()).isTrue();
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 조회된_바우처가_없다면 {

			@Test
			@DisplayName("Optional.empty를 반환한다")
			void Optional_empty를_반환한다() {
				Optional<Voucher> voucher = voucherJdbcRepository.findById(1L);
				assertThat(voucher.isEmpty());
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class deleteById메서드는 {

		@Test
		@DisplayName("바우처 아이디로 바우처를 삭제한다")
		void 바우처_아이디로_바우처를_삭제한다() {

			// given
			Voucher createdVoucher = voucherJdbcRepository.save(new FixedAmountVoucher(1L, 1000));

			// when
			int count = voucherJdbcRepository.deleteById(createdVoucher.getVoucherId());

			// then
			assertThat(count).isEqualTo(count);
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findByCreatedAt메서드는 {

		@Test
		@DisplayName("바우처 생성기간으로 바우처를 조회하고 조회된 바우처를 반환한다")
		void 바우처_생성기간으로_바우처를_조회하고_조회된_바우처를_반환한다() {

			// given
			Voucher createdVoucher = voucherJdbcRepository.save(new FixedAmountVoucher(null, 1000));
			List<Voucher> createdVouchers = Arrays.asList(createdVoucher);

			// when
			List<Voucher> vouchers = voucherJdbcRepository.findByCreatedAt(createdVoucher.getCreatedAt().toLocalDate());

			// then
			assertThat(vouchers).isEqualTo(createdVouchers);
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 조회된_바우처가_없다면 {

			@Test
			@DisplayName("빈 리스트를 반환한다")
			void 빈_리스트를_반환한다() {
				List<Voucher> vouchers = voucherJdbcRepository.findByCreatedAt(LocalDate.now());
				assertThat(vouchers.size()).isEqualTo(0);
			}
		}
	}

	@Nested
	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	class findByVoucherType메서드는 {

		@Test
		@DisplayName("바우처 타입으로 바우처를 조회하고 조회된 바우처를 반환한다")
		void 바우처_타입으로_바우처를_조회하고_조회된_바우처를_반환한다() {

			// given
			Voucher createdVoucher = voucherJdbcRepository.save(new FixedAmountVoucher(null, 1000));
			List<Voucher> createdVouchers = Arrays.asList(createdVoucher);

			// when
			List<Voucher> vouchers = voucherJdbcRepository.findByVoucherType(createdVoucher.getVoucherType());

			// then
			assertThat(vouchers).isEqualTo(createdVouchers);
		}

		@Nested
		@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
		class 조회된_바우처가_없다면 {

			@Test
			@DisplayName("빈 리스트를 반환한다")
			void 빈_리스트를_반환한다() {
				List<Voucher> vouchers = voucherJdbcRepository.findByVoucherType(FIXED_AMOUNT_VOUCHER);
				assertThat(vouchers.size()).isEqualTo(0);
			}
		}
	}
}

