package org.prgrms.springorder.service.wallet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.prgrms.springorder.domain.ErrorMessage;
import org.prgrms.springorder.domain.Message;
import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.voucher.Voucher;
import org.prgrms.springorder.domain.wallet.Wallet;
import org.prgrms.springorder.domain.wallet.WalletFactory;
import org.prgrms.springorder.exception.NoSuchCustomerException;
import org.prgrms.springorder.exception.NoSuchVoucherException;
import org.prgrms.springorder.repository.customer.CustomerRepository;
import org.prgrms.springorder.repository.voucher.VoucherJdbcRepository;
import org.prgrms.springorder.repository.voucher.VoucherRepository;
import org.prgrms.springorder.repository.wallet.WalletJdbcRepository;
import org.springframework.stereotype.Component;

@Component
public class WalletService {

	private final WalletJdbcRepository walletRepository;
	private final VoucherRepository voucherRepository;
	private final CustomerRepository customerRepository;

	public WalletService(WalletJdbcRepository walletRepository,
		VoucherRepository voucherRepository,
		CustomerRepository customerRepository) {
		this.walletRepository = walletRepository;
		this.voucherRepository = voucherRepository;
		this.customerRepository = customerRepository;
	}

	public void allocate(UUID customerId, UUID voucherId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchCustomerException(ErrorMessage.NO_SUCH_CUSTOMER_MESSAGE));
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchVoucherException(ErrorMessage.NO_SUCH_VOUCHER_MESSAGE));
		walletRepository.allocate(WalletFactory.createWallet(voucher,customer));
	}

	public List<String> findVoucherByCustomerId(UUID customerId) {
		return walletRepository.findVoucherByCustomerId(customerId)
			.stream()
			.map(Voucher::toString)
			.collect(Collectors.toList());
	}

	public void deleteByVoucherId(UUID customerId, UUID voucherId) {
		Customer customer = customerRepository.findById(customerId)
			.orElseThrow(() -> new NoSuchCustomerException(ErrorMessage.NO_SUCH_CUSTOMER_MESSAGE));
		Voucher voucher = voucherRepository.findById(voucherId)
			.orElseThrow(() -> new NoSuchVoucherException(ErrorMessage.NO_SUCH_VOUCHER_MESSAGE));
		walletRepository.delete(WalletFactory.createWallet(voucher, customer));
	}

	public List<String> findCustomerByVoucherId(UUID voucherId) {
		return walletRepository.findCustomerByVoucherId(voucherId)
			.stream()
			.map(Customer::toString)
			.collect(Collectors.toList());
	}
}
