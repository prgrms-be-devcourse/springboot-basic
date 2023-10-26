package study.dev.spring.wallet.application;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.ThrowableAssert.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import study.dev.spring.customer.domain.Customer;
import study.dev.spring.customer.domain.CustomerRepository;
import study.dev.spring.customer.exception.CustomerErrorCode;
import study.dev.spring.customer.exception.CustomerException;
import study.dev.spring.voucher.domain.VoucherRepository;
import study.dev.spring.voucher.exception.VoucherErrorCode;
import study.dev.spring.voucher.exception.VoucherException;
import study.dev.spring.voucher.fixture.VoucherFixture;
import study.dev.spring.wallet.domain.Wallet;
import study.dev.spring.wallet.domain.WalletRepository;

@DisplayName("[WalletService 테스트] - Application Layer")
class WalletServiceTest {

	private final WalletService walletService;
	private final WalletRepository walletRepository;
	private final CustomerRepository customerRepository;
	private final VoucherRepository voucherRepository;

	public WalletServiceTest() {
		walletRepository = Mockito.mock(WalletRepository.class);
		customerRepository = Mockito.mock(CustomerRepository.class);
		voucherRepository = Mockito.mock(VoucherRepository.class);
		this.walletService = new WalletService(walletRepository, customerRepository, voucherRepository);
	}

	@Nested
	@DisplayName("지갑을 추가한다")
	class addWallet {

		@Test
		@DisplayName("[지갑 추가에 성공한다]")
		void success() {
			//given
			String customerId = "customerId";
			String voucherId = "voucherId";
			Wallet wallet = Wallet.of(customerId, voucherId);

			given(customerRepository.findById(customerId))
				.willReturn(Optional.of(new Customer(customerId, "name")));
			given(voucherRepository.findById(voucherId))
				.willReturn(Optional.of(VoucherFixture.getFixedVoucher(voucherId)));
			given(walletRepository.save(any(Wallet.class)))
				.willReturn(wallet);

			//when
			String actual = walletService.addWallet(customerId, voucherId);

			//then
			assertThat(actual).isEqualTo(wallet.getUuid());
		}

		@Nested
		@DisplayName("[지갑 추가에 실패한다]")
		class fail {

			@Test
			@DisplayName("[고객아이디에 맞는 고객이 존재하지 않아 실패한다]")
			void failByCustomerNotExist() {
				//given
				given(customerRepository.findById(anyString()))
					.willReturn(Optional.empty());

				//when
				ThrowingCallable when = () -> walletService.addWallet("customerId", "voucherId");

				//then
				assertThatThrownBy(when)
					.isInstanceOf(CustomerException.class)
					.hasMessageContaining(CustomerErrorCode.NOT_EXIST.getMessage());
			}

			@Test
			@DisplayName("[바우처아이디 맞는 바우처가 존재하지 않아 실패한다]")
			void failByVoucherNotExist() {
				//given
				String customerId = "customerId";

				given(customerRepository.findById(customerId))
					.willReturn(Optional.of(new Customer(customerId, "name")));
				given(voucherRepository.findById(anyString()))
					.willReturn(Optional.empty());

				//when
				ThrowingCallable when = () -> walletService.addWallet(customerId, "voucherId");

				//then
				assertThatThrownBy(when)
					.isInstanceOf(VoucherException.class)
					.hasMessageContaining(VoucherErrorCode.NOT_EXIST.getMessage());
			}
		}
	}
}