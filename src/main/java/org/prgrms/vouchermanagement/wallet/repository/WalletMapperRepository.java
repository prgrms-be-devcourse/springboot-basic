package org.prgrms.vouchermanagement.wallet.repository;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.dto.WalletCreateInfo;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.wallet.domain.WalletMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletMapperRepository {

    int create(WalletCreateInfo walletCreateInfo);

    List<Voucher> findVouchers(UUID customerId);

    int delete(UUID customerId);

    List<WalletMapper> findAll();

    Optional<Customer> findCustomer(UUID voucherId);
}
