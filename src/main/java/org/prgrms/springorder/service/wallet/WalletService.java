package org.prgrms.springorder.service.wallet;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.prgrms.springorder.domain.ErrorMessage;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.exception.NoSuchCustomerException;
import org.prgrms.springorder.repository.wallet.WalletRepository;
import org.springframework.stereotype.Component;

@Component
public class WalletService {

	private final WalletRepository walletRepository;

	public WalletService(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
	}

	public void allocate(UUID customerId, UUID voucherId) {
		walletRepository.updateCustomerIdByVoucherId(customerId, voucherId);
	}

	public List<String> getCustomerVouchers(UUID customerId) {
		return walletRepository.findVouchersByCustomerId(customerId)
			.stream()
			.map(Voucher::toString)
			.collect(Collectors.toList());
	}

	public void deleteByVoucherId(UUID customerId, UUID voucherId) {
		walletRepository.delete(customerId, voucherId);
	}

	public Customer findByVoucherId(UUID voucherId) {
		return walletRepository.findByVoucherId(voucherId)
			.orElseThrow(() -> new NoSuchCustomerException(ErrorMessage.NO_SUCH_CUSTOMER_MESSAGE));
	}
}
