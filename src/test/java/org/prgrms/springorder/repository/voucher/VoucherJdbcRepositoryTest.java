package org.prgrms.springorder.repository.voucher;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class VoucherJdbcRepositoryTest {

	DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
			.addScript("schema.sql")
			.build();
	}

	NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}

	VoucherJdbcRepository voucherJdbcRepository;

	@BeforeAll
	void setUp() {
		voucherJdbcRepository = new VoucherJdbcRepository(namedParameterJdbcTemplate());
	}

	@AfterEach
	void clear() {
		voucherJdbcRepository.clear();
	}

	@Test
	@DisplayName("바우처를 저장하고 성공적으로 조회한다.")
	void saveTest() {

		//given
		double value = 150;
		Voucher fixedAmountVoucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value);

		//when
		voucherJdbcRepository.save(fixedAmountVoucher);
		Optional<Voucher> savedVoucher = voucherJdbcRepository.findById(fixedAmountVoucher.getVoucherId());

		//then
		assertThat(savedVoucher).isPresent();
		Voucher voucher = savedVoucher.get();
		assertThat(voucher).isEqualTo(fixedAmountVoucher);
	}

	@Test
	@DisplayName("바우처 조회를 실패한다.")
	void findByIdTest() {

		//given
		double value = 150;
		Voucher fixedAmountVoucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value);

		//when
		voucherJdbcRepository.save(fixedAmountVoucher);
		Optional<Voucher> savedVoucher = voucherJdbcRepository.findById(UUID.randomUUID());

		//then
		assertThat(savedVoucher).isEmpty();

	}

	@Test
	@DisplayName("바우처를 저장하고 리스트로 성공적으로 조회한다.")
	void findAllTest() {

		//given
		double value1 = 100;
		double value2 = 40;

		Voucher fixedAmountVoucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value1);
		Voucher percentAmountVoucher = VoucherFactory.createVoucher(VoucherType.PERCENT_DISCOUNT, value2);

		//when
		voucherJdbcRepository.save(fixedAmountVoucher);
		voucherJdbcRepository.save(percentAmountVoucher);
		List<Voucher> voucherList = voucherJdbcRepository.findAll();

		//then
		assertThat(voucherList).hasSize(2);
		assertThat(voucherList).contains(fixedAmountVoucher).contains(percentAmountVoucher);
	}

	@Test
	@DisplayName("바우처를 성공적으로 삭제한다.")
	void deleteTest() {

		//given
		double value1 = 100;
		Voucher fixedAmountVoucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value1);

		//when
		voucherJdbcRepository.save(fixedAmountVoucher);
		voucherJdbcRepository.deleteById(fixedAmountVoucher.getVoucherId());
		Optional<Voucher> deletedVoucher = voucherJdbcRepository.findById(fixedAmountVoucher.getVoucherId());

		//then
		assertThat(deletedVoucher).isEmpty();
	}

	@Test
	@DisplayName("바우처를 성공적으로 업데이트한다.")
	void updateTest() {

		//given
		double value = 100;
		double updateValue = 50;
		Voucher fixedAmountVoucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value);

		//when
		voucherJdbcRepository.save(fixedAmountVoucher);
		fixedAmountVoucher.editVoucherValue(updateValue);
		voucherJdbcRepository.updateByObject(fixedAmountVoucher);
		Optional<Voucher> savedVoucher = voucherJdbcRepository.findById(fixedAmountVoucher.getVoucherId());

		//then
		assertThat(savedVoucher).isPresent();
		Voucher voucher = savedVoucher.get();
		assertThat(voucher).isEqualTo(fixedAmountVoucher);
	}

}