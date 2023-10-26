package org.prgrms.vouchermanagement.wallet;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    int create(WalletCreateInfo walletCreateInfo);

    List<Voucher> findVouchers(UUID customerId);

    int delete(UUID customerId);

    Customer findCustomer(UUID voucherId);
}
