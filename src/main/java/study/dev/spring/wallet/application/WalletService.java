package study.dev.spring.wallet.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import study.dev.spring.customer.domain.CustomerRepository;
import study.dev.spring.customer.exception.CustomerErrorCode;
import study.dev.spring.customer.exception.CustomerException;
import study.dev.spring.voucher.domain.VoucherRepository;
import study.dev.spring.voucher.exception.VoucherErrorCode;
import study.dev.spring.voucher.exception.VoucherException;
import study.dev.spring.wallet.domain.Wallet;
import study.dev.spring.wallet.domain.WalletRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class WalletService {

	private final WalletRepository walletRepository;
	private final CustomerRepository customerRepository;
	private final VoucherRepository voucherRepository;

	public String addWallet(
		String customerId,
		String voucherId
	) {
		validateCustomerExist(customerId);
		validateVoucherExist(voucherId);

		Wallet wallet = Wallet.of(customerId, voucherId);
		return walletRepository.save(wallet).getUuid();
	}

	public boolean deleteByCustomer(String customerId) {
		walletRepository.deleteByCustomerId(customerId);
		return true;
	}

	private void validateCustomerExist(String customerId) {
		customerRepository.findById(customerId)
			.orElseThrow(() -> new CustomerException(CustomerErrorCode.NOT_EXIST));
	}
	private void validateVoucherExist(String voucherId) {
		voucherRepository.findById(voucherId)
			.orElseThrow(() -> new VoucherException(VoucherErrorCode.NOT_EXIST));
	}
}
