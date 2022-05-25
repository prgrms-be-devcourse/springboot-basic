package com.programmers.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.programmers.order.domain.Customer;
import com.programmers.order.domain.Wallet;
import com.programmers.order.domain.Voucher;
import com.programmers.order.exception.JdbcException;
import com.programmers.order.message.LogMessage;
import com.programmers.order.repository.wallet.WalletRepository;

@Transactional(readOnly = true)
@Service
public class WalletService {
	private static final Logger log = LoggerFactory.getLogger(WalletService.class);

	private final WalletRepository walletRepository;

	public WalletService(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
	}

	@Transactional
	public Optional<UUID> save(UUID customerId, UUID voucherId) {
		Wallet wallet = new Wallet(UUID.randomUUID(), customerId,
				voucherId, LocalDateTime.now());

		try {
			walletRepository.insert(wallet);

			return Optional.ofNullable(wallet.getWalletId());
		} catch (JdbcException.NotExecuteQuery e) {
			e.printStackTrace();
			log.error(LogMessage.ErrorLogMessage.getPrefix(), LogMessage.ErrorLogMessage.NOT_EXECUTE_QUERY);

			return Optional.empty();
		}
	}

	public boolean isDuplicatePublish(UUID customerId, UUID voucherId) {
		return walletRepository.isDuplicatePublish(customerId, voucherId);
	}

	public List<Voucher> getVouchersForCustomer(UUID customerId) {
		return walletRepository.findVouchersByCustomerId(customerId);
	}

	public List<Customer> getCustomerForVoucher(UUID voucherId) {
		return walletRepository.joinCustomers(voucherId);
	}

	public Optional<Voucher> findVoucherById(UUID voucherId) {
		return walletRepository.findVoucherByVoucherId(voucherId);
	}

	@Transactional
	public void deleteByCustomerIdAndVoucherId(UUID customerIdentity, UUID voucherIdentity) {
		walletRepository.deleteByCustomerIdAndVoucherId(customerIdentity, voucherIdentity);
	}
}
