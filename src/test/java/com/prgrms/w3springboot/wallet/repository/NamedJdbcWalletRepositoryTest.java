package com.prgrms.w3springboot.wallet.repository;

import static com.wix.mysql.EmbeddedMysql.*;
import static com.wix.mysql.ScriptResolver.*;
import static com.wix.mysql.config.Charset.*;
import static com.wix.mysql.config.MysqldConfig.*;
import static com.wix.mysql.distribution.Version.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
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

import com.prgrms.w3springboot.customer.Customer;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.wallet.Wallet;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.zaxxer.hikari.HikariDataSource;

@SpringJUnitConfig
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NamedJdbcWalletRepositoryTest {
	private static final UUID NEW_WALLET_ID = UUID.randomUUID();
	private static final UUID EXISTING_CUSTOMER_ID = UUID.fromString("75810d66-19bb-11ec-9621-0242ac130002");
	private static final UUID EXISTING_VOUCHER_ID = UUID.fromString("9cbb3d0a-158a-11ec-82a8-0242ac130003");

	static EmbeddedMysql embeddedMysql;

	@Autowired
	WalletRepository walletRepository;

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

	@DisplayName("특정 고객에게 바우처를 할당한다.")
	@Test
	void testInsertWallet() {
		// given
		Wallet wallet = new Wallet(NEW_WALLET_ID, EXISTING_CUSTOMER_ID, EXISTING_VOUCHER_ID);

		// when
		Wallet inserted = walletRepository.insert(wallet);

		// then
		assertThat(inserted.getWalletId()).isEqualTo(NEW_WALLET_ID);
	}

	@DisplayName("고객ID로 바우처 목록을 조회한다.")
	@Test
	void testFindVouchersByCustomerId() {
		// when
		List<Voucher> vouchers = walletRepository.findVouchersByCustomerId(EXISTING_CUSTOMER_ID);

		// then
		assertThat(vouchers).hasSize(1);
	}

	@DisplayName("고객에게 할당된 바우처를 삭제한다.")
	@Test
	void testDeleteVoucherFromWallet() {
		// given
		UUID walletId = UUID.randomUUID();
		Wallet wallet = new Wallet(walletId, EXISTING_CUSTOMER_ID, EXISTING_VOUCHER_ID);
		walletRepository.insert(wallet);
		List<Voucher> beforeVouchers = walletRepository.findVouchersByCustomerId(EXISTING_CUSTOMER_ID);
		assertThat(beforeVouchers).hasSize(2);

		// when
		walletRepository.delete(walletId);
		List<Voucher> afterVouchers = walletRepository.findVouchersByCustomerId(EXISTING_CUSTOMER_ID);

		// then
		assertThat(afterVouchers).hasSize(1);
	}

	@DisplayName("특정 바우처를 보유한 고객을 조회한다.")
	@Test
	void testFindCustomersByVoucherId() {
		// when
		List<Customer> customers = walletRepository.findCustomersByVoucherId(EXISTING_VOUCHER_ID);

		// then
		assertThat(customers).hasSize(1);
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
		public WalletRepository walletRepository(
			NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
			return new NamedJdbcWalletRepository(namedParameterJdbcTemplate);
		}
	}
}