package com.prgrms.w3springboot.wallet.service;

import java.util.List;
import java.util.UUID;

import com.prgrms.w3springboot.customer.Customer;
import com.prgrms.w3springboot.voucher.Voucher;
import com.prgrms.w3springboot.wallet.Wallet;
import com.prgrms.w3springboot.wallet.dto.WalletRequestDto;
import com.prgrms.w3springboot.wallet.repository.WalletRepository;

public class WalletService {
	private final WalletRepository walletRepository;

	public WalletService(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
	}

	public Wallet allocateVoucherToCustomer(WalletRequestDto walletRequestDto) {
		Wallet wallet = walletRequestDto.toEntity();
		return walletRepository.insert(wallet);
	}

	public List<Voucher> findVouchersByCustomerId(UUID customerId) {
		return walletRepository.findVouchersByCustomerId(customerId);
	}

	public void deleteVoucher(WalletRequestDto walletRequestDto) {
		walletRepository.delete(walletRequestDto.getWalletId());
	}

	public List<Customer> findCustomersByVoucherId(UUID voucherId) {
		return walletRepository.findCustomersByVoucherId(voucherId);
	}
}
