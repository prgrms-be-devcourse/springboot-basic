package org.prgrms.springorder.controller.wallet;

import java.util.List;
import java.util.UUID;

import org.prgrms.springorder.service.wallet.WalletService;
import org.springframework.stereotype.Component;

@Component
public class WalletController {

	private final WalletService walletService;

	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}

	public void allocateVoucher(UUID customerId, UUID voucherId) {
		walletService.allocate(customerId, voucherId);
	}

	public List<String> getCustomerVouchers(UUID customerId) {
		return walletService.findVoucherByCustomerId(customerId);
	}

	public void deleteByVoucherId(UUID customerId, UUID voucherId) {
		walletService.deleteByVoucherId(customerId,voucherId);
	}

	public List<String> findByVoucherId(UUID voucherId) {
		return walletService.findCustomerByVoucherId(voucherId);
	}
}
