package study.dev.spring.wallet.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;

import study.dev.spring.customer.domain.Customer;
import study.dev.spring.customer.domain.CustomerRepository;
import study.dev.spring.customer.infrastructure.JdbcCustomerRepository;
import study.dev.spring.global.support.DataSourceTestSupport;
import study.dev.spring.voucher.domain.Voucher;
import study.dev.spring.voucher.domain.VoucherRepository;
import study.dev.spring.voucher.fixture.VoucherFixture;
import study.dev.spring.voucher.infrastructure.JdbcVoucherRepository;
import study.dev.spring.wallet.infrastructure.JdbcWalletRepository;

@DisplayName("[WalletRepository 테스트] - Domain Layer")
@TestInstance(Lifecycle.PER_CLASS)
class WalletRepositoryTest extends DataSourceTestSupport {

	private final WalletRepository walletRepository;
	private final CustomerRepository customerRepository;
	private final VoucherRepository voucherRepository;

	private Customer customer;
	private Voucher voucher;

	public WalletRepositoryTest(@Autowired DataSource dataSource) {
		this.walletRepository = new JdbcWalletRepository(dataSource);
		this.customerRepository = new JdbcCustomerRepository(dataSource);
		this.voucherRepository = new JdbcVoucherRepository(dataSource);
	}

	@BeforeAll
	void setUp() {
		this.customer = new Customer(UUID.randomUUID().toString(), "customer_name");
		this.voucher = VoucherFixture.getFixedVoucher();

		customerRepository.save(customer);
		voucherRepository.save(voucher);
	}

	@Test
	@DisplayName("[Wallet 을 저장한다]")
	void save() {
		//given
		Wallet wallet = Wallet.of(customer.getUuid(), voucher.getUuid());

		//when
		Wallet actual = walletRepository.save(wallet);

		//then
		assertThat(actual).isEqualTo(wallet);

		Optional<Wallet> findWallet = walletRepository.findById(wallet.getUuid());
		assertThat(findWallet).isPresent();
		assertWallet(findWallet.get(), wallet);
	}

	@Nested
	@DisplayName("[아이디로 Wallet 을 조회한다]")
	class findById {

		@Test
		@DisplayName("[성공적을 조회한다]")
		void success() {
			//given
			Wallet wallet = Wallet.of(customer.getUuid(), voucher.getUuid());
			walletRepository.save(wallet);

			//when
			Optional<Wallet> actual = walletRepository.findById(wallet.getUuid());

			//then
			assertThat(actual).isPresent();

			Wallet actualWallet = actual.get();
			assertWallet(wallet, actualWallet);
		}

		@Test
		@DisplayName("[아이디에 해당하는 Wallet 이 존재하지않아 빈 값을 내린다]")
		void emptyValue() {
			//given
			Wallet wallet = Wallet.of(customer.getUuid(), voucher.getUuid());

			//when
			Optional<Wallet> actual = walletRepository.findById(wallet.getUuid());

			//then
			assertThat(actual).isNotPresent();
		}
	}

	@Test
	@DisplayName("[고객 아이디로 Wallet 을 조회한다]")
	void findByCustomerId() {
		//given
		Voucher percentVoucher = VoucherFixture.getPercentVoucher();
		voucherRepository.save(percentVoucher);
		List<Wallet> wallets = List.of(
			walletRepository.save(Wallet.of(customer.getUuid(), voucher.getUuid())),
			walletRepository.save(Wallet.of(customer.getUuid(), percentVoucher.getUuid()))
		);

		//when
		List<Wallet> actual = walletRepository.findByCustomerId(customer.getUuid());

		//then
		assertThat(actual).hasSize(2);
		for (int i = 0; i < 2; i++) {
			assertWallet(actual.get(i), wallets.get(i));
		}
	}

	@Test
	@DisplayName("[바우처 아이디로 Wallet 을 조회한다]")
	void findByVoucherId() {
		//given
		String customerId = "uuid";
		customerRepository.save(new Customer(customerId, "name"));

		List<Wallet> wallets = List.of(
			walletRepository.save(Wallet.of(customerId, voucher.getUuid())),
			walletRepository.save(Wallet.of(customer.getUuid(), voucher.getUuid()))
		);

		//when
		List<Wallet> actual = walletRepository.findByVoucherId(voucher.getUuid());

		//then
		assertThat(actual).hasSize(2);
		for (int i = 0; i < 2; i++) {
			assertWallet(actual.get(i), wallets.get(i));
		}
	}

	@Test
	@DisplayName("[고객 아이디로 Wallet 을 삭제한다]")
	void deleteByCustomerId() {
		//given
		walletRepository.save(Wallet.of(customer.getUuid(), voucher.getUuid()));

		//when
		walletRepository.deleteByCustomerId(customer.getUuid());

		//then
		List<Wallet> actual = walletRepository.findByCustomerId(customer.getUuid());
		assertThat(actual).isEmpty();
	}

	private void assertWallet(Wallet actual, Wallet expected) {
		assertAll(
			() -> assertThat(actual.getUuid()).isEqualTo(expected.getUuid()),
			() -> assertThat(actual.getCustomerId()).isEqualTo(expected.getCustomerId()),
			() -> assertThat(actual.getVoucherId()).isEqualTo(expected.getVoucherId())
		);
	}
}