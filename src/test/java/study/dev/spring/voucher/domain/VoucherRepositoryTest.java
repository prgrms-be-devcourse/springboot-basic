package study.dev.spring.voucher.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import study.dev.spring.global.support.DataSourceTestSupport;
import study.dev.spring.voucher.fixture.VoucherFixture;
import study.dev.spring.voucher.infrastructure.JdbcVoucherRepository;

@DisplayName("[VoucherRepository 테스트] - Domain Layer")
class VoucherRepositoryTest extends DataSourceTestSupport {

	private final VoucherRepository voucherRepository;

	public VoucherRepositoryTest(@Autowired DataSource dataSource) {
		this.voucherRepository = new JdbcVoucherRepository(dataSource);
	}

	@Test
	@DisplayName("[바우처를 저장한다]")
	void save() {
		//given
		Voucher voucher = VoucherFixture.getFixedVoucher();

		//when
		voucherRepository.save(voucher);

		//then
		Optional<Voucher> actual = voucherRepository.findById(voucher.getUuid());
		assertThat(actual).isPresent();
	}

	@Nested
	@DisplayName("[아이디로 바우처를 조회한다]")
	class findById {

		@Test
		@DisplayName("[조회에 성공한다]")
		void success() {
			//given
			Voucher voucher = VoucherFixture.getFixedVoucher();
			voucherRepository.save(voucher);

			//when
			Optional<Voucher> actual = voucherRepository.findById(voucher.getUuid());

			//then
			assertThat(actual).isPresent();
			Voucher actualVoucher = actual.get();
			assertVoucher(voucher, actualVoucher);
		}

		@Test
		@DisplayName("[바우처가 존재하지 않아 빈 값을 반환한다]")
		void emptyValue() {
			//given
			Voucher voucher = VoucherFixture.getFixedVoucher();

			//when
			Optional<Voucher> actual = voucherRepository.findById(voucher.getUuid());

			//then
			assertThat(actual).isNotPresent();
		}
	}

	@Test
	@DisplayName("[모든 바우처를 조회한다]")
	void findAll() {
		//given
		Voucher voucher1 = VoucherFixture.getFixedVoucher();
		Voucher voucher2 = VoucherFixture.getPercentVoucher();

		List<Voucher> vouchers = List.of(voucher1, voucher2);
		vouchers.forEach(voucherRepository::save);

		//when
		List<Voucher> actual = voucherRepository.findAll();

		//then
		assertThat(actual).hasSameSizeAs(vouchers);
		for (int i = 0; i < actual.size(); i++) {
			assertVoucher(actual.get(i), vouchers.get(i));
		}
	}

	@Test
	@DisplayName("[아이디로 바우처를 삭제한다]")
	void deleteById() {
		//given
		Voucher voucher = VoucherFixture.getFixedVoucher();
		voucherRepository.save(voucher);

		//when
		voucherRepository.deleteById(voucher.getUuid());

		//then
		List<Voucher> actual = voucherRepository.findAll();
		assertThat(actual).isEmpty();
	}

	private void assertVoucher(Voucher actual, Voucher expected) {
		assertAll(
			() -> assertThat(actual.getUuid()).isEqualTo(expected.getUuid()),
			() -> assertThat(actual.getName()).isEqualTo(expected.getName()),
			() -> assertThat(actual.getTypeName()).isEqualTo(expected.getTypeName()),
			() -> assertThat(actual.getDiscountAmount()).isEqualTo(expected.getDiscountAmount())
		);
	}
}