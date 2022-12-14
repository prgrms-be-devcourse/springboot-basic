package com.programmers.voucher.domain.wallet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.programmers.voucher.domain.customer.model.Customer;
import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.wallet.model.Wallet;
import com.programmers.voucher.domain.wallet.repository.WalletRepository;

@Service
public class WalletService {

	private final WalletRepository walletRepository;

	public WalletService(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
	}

	public Wallet register(UUID customerId, UUID voucherId) {
		Wallet wallet = new Wallet(customerId, voucherId, LocalDateTime.now());
		walletRepository.save(wallet);
		return wallet;
	}

	public List<Voucher> findVouchersByCustomerId(UUID customerId) {
		return walletRepository.findVouchersByCustomerId(customerId);
	}

	public List<Customer> findCustomersByVoucherId(UUID voucherId) {
		return walletRepository.findCustomersByVoucherId(voucherId);
	}

	public void deleteByCustomerId(UUID customerId) {
		walletRepository.deleteByCustomerId(customerId);
	}
}
