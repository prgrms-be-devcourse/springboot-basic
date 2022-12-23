package com.programmers.voucher.domain.voucher.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.util.VoucherFactory;

@SpringJUnitConfig
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcVoucherRepositoryTest {

	@Autowired
	JdbcVoucherRepository voucherRepository;

	@Configuration
	@ComponentScan(basePackages = "com.programmers.voucher.domain.voucher")
	static class Config {
		@Bean
		DataSource dataSource() {
			return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
				.addScript("schema.sql")
				.build();
		}

		@Bean
		NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
			return new NamedParameterJdbcTemplate(dataSource());
		}
	}

	@AfterEach
	void clearRepository() {
		voucherRepository.deleteAll();
	}

	@Test
	void saveTest() {
		Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, "1000");

		Voucher createdVoucher = voucherRepository.save(voucher);

		assertThat(createdVoucher).isEqualTo(voucher);
	}

	@Test
	void findByIdTest() {
		Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED, "1000");
		Voucher createdVoucher = voucherRepository.save(voucher);

		Voucher findVoucher = voucherRepository.findById(createdVoucher.getVoucherId());

		assertThat(findVoucher.toString()).isEqualTo(createdVoucher.toString());
	}

	@Test
	void deleteByIdTest() {
		Voucher fixedVoucher = VoucherFactory.createVoucher(VoucherType.FIXED, "1000");
		Voucher normalVoucher = VoucherFactory.createVoucher(VoucherType.PERCENT, "20");
		voucherRepository.save(fixedVoucher);
		voucherRepository.save(normalVoucher);
		List<Voucher> beforeDelete = voucherRepository.findAll();

		voucherRepository.deleteById(fixedVoucher.getVoucherId());
		voucherRepository.deleteById(normalVoucher.getVoucherId());
		List<Voucher> afterDelete = voucherRepository.findAll();

		assertThat(afterDelete.size()).isEqualTo(beforeDelete.size() - 2);
	}

	@Test
	void findAllTest() {
		Voucher fixedVoucher = VoucherFactory.createVoucher(VoucherType.FIXED, "1000");
		Voucher normalVoucher = VoucherFactory.createVoucher(VoucherType.PERCENT, "20");

		List<Voucher> beforeSave = voucherRepository.findAll();

		voucherRepository.save(fixedVoucher);
		voucherRepository.save(normalVoucher);
		List<Voucher> afterSave = voucherRepository.findAll();

		assertThat(afterSave.size()).isEqualTo(beforeSave.size() + 2);
	}
}