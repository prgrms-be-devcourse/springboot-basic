package com.prgrms.w3springboot.voucher.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.ScriptResolver.*;
import static com.wix.mysql.config.Charset.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.prgrms.w3springboot.voucher.FixedAmountVoucher;
import com.prgrms.w3springboot.voucher.PercentAmountVoucher;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.voucher.VoucherType;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NamedJdbcVoucherRepositoryTest {

	private static final UUID NEW_VOUCHER_ID = UUID.randomUUID();
	private static final UUID EXISTING_VOUCHER_ID = UUID.fromString(
		"da92052e-170a-11ec-9621-0242ac130002");
	static EmbeddedMysql embeddedMysql;

	@Autowired
	VoucherRepository voucherRepository;

	@BeforeAll
	void setup() {
		MysqldConfig mysqlConfig = aMysqldConfig(v8_0_11)
			.withCharset(UTF8)
			.withPort(2215)
			.withUser("test", "test1234!")
			.withTimeZone("Asia/Seoul")
			.build();

		embeddedMysql = anEmbeddedMysql(mysqlConfig)
			.addSchema("test-devcourse", classPathScript("schema-jdbc.sql"))
			.start();
	}

	@AfterAll
	void cleanup() {
		embeddedMysql.stop();
	}

	@DisplayName("바우처 ID로 조회한다.")
	@Test
	void testFindById() {
		Optional<Voucher> foundVoucher = voucherRepository.findById(EXISTING_VOUCHER_ID);

		assertThat(foundVoucher).isPresent();
	}

	@DisplayName("바우처를 생성한다.")
	@Test
	void testInsert() {
		Voucher voucher = new FixedAmountVoucher(NEW_VOUCHER_ID, 20, VoucherType.FIXED);

		Voucher insertedVoucher = voucherRepository.insert(voucher);
		assertThat(insertedVoucher.getVoucherId()).isEqualTo(NEW_VOUCHER_ID);

		Optional<Voucher> existVoucher = voucherRepository.findById(NEW_VOUCHER_ID);
		assertThat(existVoucher)
			.isPresent()
			.get()
			.hasFieldOrPropertyWithValue("voucherId", NEW_VOUCHER_ID);
	}

	@DisplayName("바우처 할인 수치를 수정한다.")
	@Test
	void testUpdate() {
		Voucher voucher = new FixedAmountVoucher(EXISTING_VOUCHER_ID, 35L, VoucherType.FIXED);

		Voucher retrievedVoucher = voucherRepository.update(voucher);

		assertThat(retrievedVoucher)
			.isNotNull()
			.hasFieldOrPropertyWithValue("voucherId", EXISTING_VOUCHER_ID)
			.hasFieldOrPropertyWithValue("amount", 35L);
	}

	@DisplayName("바우처를 삭제한다.")
	@Test
	void testDelete() {
		Voucher voucher = new PercentAmountVoucher(NEW_VOUCHER_ID, 45, VoucherType.PERCENT);
		Voucher newVoucher = voucherRepository.insert(voucher);
		assertThat(newVoucher.getVoucherId()).isEqualTo(NEW_VOUCHER_ID);

		Optional<Voucher> insertedVoucher = voucherRepository.findById(NEW_VOUCHER_ID);
		assertThat(insertedVoucher).isPresent();

		voucherRepository.delete(NEW_VOUCHER_ID);
		Optional<Voucher> nonExistVoucher = voucherRepository.findById(NEW_VOUCHER_ID);

		assertThat(nonExistVoucher).isNotPresent();
	}

	@DisplayName("모든 바우처를 조회한다.")
	@Test
	void testFindAll() {
		List<Voucher> vouchers = voucherRepository.findAll();

		assertThat(vouchers)
			.isNotEmpty()
			.hasSize(3);
	}

	@Configuration
	static class Config {

		@Bean
		public DataSource dataSource() {
			return DataSourceBuilder.create()
				.url("jdbc:mysql://localhost:2215/test-devcourse")
				.username("test")
				.password("test1234!")
				.type(HikariDataSource.class)
				.build();
		}

		@Bean
		public JdbcTemplate jdbcTemplate(DataSource dataSource) {
			return new JdbcTemplate(dataSource);
		}

		@Bean
		public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
			return new NamedParameterJdbcTemplate(jdbcTemplate);
		}

		@Bean
		public VoucherRepository voucherRepository(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			return new NamedJdbcVoucherRepository(namedParameterJdbcTemplate);
		}
	}
}