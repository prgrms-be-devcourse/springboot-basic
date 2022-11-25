package org.prgrms.springorder.domain.wallet;

import java.time.LocalDateTime;
import java.util.UUID;

import org.prgrms.springorder.domain.customer.Customer;
import org.prgrms.springorder.domain.voucher.Voucher;

public class WalletFactory {

	public static Wallet createWallet(UUID uuid, Voucher voucher, Customer customer, LocalDateTime localDateTime) {
		return new Wallet(uuid, voucher, customer, localDateTime);
	}

	public static Wallet createWallet(Voucher voucher, Customer customer) {
		return createWallet(UUID.randomUUID(), voucher, customer, LocalDateTime.now());
	}
}
