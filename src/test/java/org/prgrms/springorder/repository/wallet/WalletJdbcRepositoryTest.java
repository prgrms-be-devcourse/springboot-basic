package org.prgrms.springorder.repository.wallet;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.prgrms.springorder.domain.VoucherFactory;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.customer.CustomerType;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.voucher.VoucherType;
import org.prgrms.springorder.domain.wallet.Wallet;
import org.prgrms.springorder.repository.customer.CustomerJdbcRepository;
import org.prgrms.springorder.repository.voucher.VoucherJdbcRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletJdbcRepositoryTest {

	DataSource dataSource() {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
			.addScript("schema.sql")
			.build();
	}

	NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
		return new NamedParameterJdbcTemplate(dataSource());
	}

	WalletJdbcRepository walletJdbcRepository;
	CustomerJdbcRepository customerRepository;
	VoucherJdbcRepository voucherJdbcRepository;

	@BeforeAll
	void setUp() {
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = namedParameterJdbcTemplate();
		walletJdbcRepository = new WalletJdbcRepository(namedParameterJdbcTemplate);
		customerRepository = new CustomerJdbcRepository(namedParameterJdbcTemplate);
		voucherJdbcRepository = new VoucherJdbcRepository(namedParameterJdbcTemplate);
	}

	@AfterEach
	void clear() {
		walletJdbcRepository.clear();
		voucherJdbcRepository.clear();
		customerRepository.clear();
	}

	@Test
	void allocateTest() {

		double value = 150;
		Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value);
		voucherJdbcRepository.save(voucher);

		UUID customerId = UUID.randomUUID();
		String name = "이건우";
		String email = "1234";
		Customer customer = new Customer(customerId, name, email, LocalDateTime.now(), CustomerType.NORMAL);
		customerRepository.save(customer);

		UUID walletId = UUID.randomUUID();

		Wallet wallet = new Wallet(walletId, voucher, customer, LocalDateTime.now());

		walletJdbcRepository.allocate(wallet);

		List<Customer> customerList = walletJdbcRepository.findCustomerByVoucherId(voucher.getVoucherId());

		assertThat(customerList).contains(customer);

	}

	@Test
	void findVoucherByCustomerIdTest() {
		double value = 150;
		Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value);
		voucherJdbcRepository.save(voucher);

		UUID customerId = UUID.randomUUID();
		String name = "이건우";
		String email = "1234";
		Customer customer = new Customer(customerId, name, email, LocalDateTime.now(), CustomerType.NORMAL);
		customerRepository.save(customer);

		UUID walletId = UUID.randomUUID();

		Wallet wallet = new Wallet(walletId, voucher, customer, LocalDateTime.now());

		walletJdbcRepository.allocate(wallet);

		List<Voucher> customerList = walletJdbcRepository.findVoucherByCustomerId(customerId);

		assertThat(customerList).contains(voucher);
	}

	@Test
	void deleteTest() {
		double value = 150;
		Voucher voucher = VoucherFactory.createVoucher(VoucherType.FIXED_AMOUNT, value);
		voucherJdbcRepository.save(voucher);

		UUID customerId = UUID.randomUUID();
		String name = "이건우";
		String email = "1234";
		Customer customer = new Customer(customerId, name, email, LocalDateTime.now(), CustomerType.NORMAL);
		customerRepository.save(customer);

		UUID walletId = UUID.randomUUID();

		Wallet wallet = new Wallet(walletId, voucher, customer, LocalDateTime.now());

		walletJdbcRepository.allocate(wallet);
		walletJdbcRepository.delete(wallet);

		List<Voucher> customerList = walletJdbcRepository.findVoucherByCustomerId(customerId);
		assertThat(customerList).doesNotContain(voucher);
	}


}