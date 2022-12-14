package com.programmers.voucher.domain.wallet.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
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

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.customer.model.CustomerType;
import com.programmers.voucher.domain.customer.repository.JdbcCustomerRepository;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.repository.JdbcVoucherRepository;
import com.programmers.voucher.domain.voucher.util.VoucherFactory;
import com.programmers.voucher.domain.wallet.model.Wallet;

@SpringJUnitConfig
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JdbcWalletRepositoryTest {

	@Autowired
	JdbcVoucherRepository voucherRepository;
	@Autowired
	JdbcCustomerRepository customerRepository;
	@Autowired
	JdbcWalletRepository walletRepository;

	UUID customerId = UUID.randomUUID();
	UUID voucherId = UUID.randomUUID();

	@Configuration
	@ComponentScan(basePackages = "com.programmers.voucher.domain")
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

	@BeforeAll
	void setTestData() {
		Voucher voucher = VoucherFactory.createVoucher(voucherId, VoucherType.FIXED, "1000", LocalDateTime.now());
		Customer customer = new Customer(customerId, LocalDateTime.now(), CustomerType.NORMAL,
			LocalDateTime.now());
		voucherRepository.save(voucher);
		customerRepository.save(customer);
	}

	@AfterEach
	void clearRepository() {
		walletRepository.clear();
	}

	@Test
	void save() {
		Wallet wallet = new Wallet(customerId, voucherId, LocalDateTime.now());

		Wallet createdWallet = walletRepository.save(wallet);

		assertThat(createdWallet).isEqualTo(wallet);
	}

	@Test
	void findVouchersByCustomerId() {
		List<Voucher> before = walletRepository.findVouchersByCustomerId(customerId);
		Wallet wallet = new Wallet(customerId, voucherId, LocalDateTime.now());
		walletRepository.save(wallet);

		List<Voucher> after = walletRepository.findVouchersByCustomerId(customerId);

		assertThat(after.size()).isEqualTo(before.size() + 1);
	}

	@Test
	void findCustomersByVoucherId() {
		List<Customer> before = walletRepository.findCustomersByVoucherId(voucherId);
		Wallet wallet = new Wallet(customerId, voucherId, LocalDateTime.now());
		walletRepository.save(wallet);

		List<Customer> after = walletRepository.findCustomersByVoucherId(voucherId);

		assertThat(after.size()).isEqualTo(before.size() + 1);
	}

	@Test
	void deleteByCustomerId() {
		walletRepository.deleteByCustomerId(customerId);
		List<Voucher> afterDelete = walletRepository.findVouchersByCustomerId(customerId);

		assertThat(afterDelete.size()).isEqualTo(0);
	}
}