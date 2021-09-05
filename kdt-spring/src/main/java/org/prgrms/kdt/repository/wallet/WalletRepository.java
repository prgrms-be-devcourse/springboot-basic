package org.prgrms.kdt.repository.wallet;

import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.wallet.Wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

    Wallet insert(UUID customerId, UUID voucherId);
    Wallet findByCustomerId(UUID customerId);
    List<Wallet> findByVoucherId(UUID voucherId);
    void delete(UUID customerId, UUID voucherId);
    void deleteAll();
    boolean exist(UUID customerId, UUID voucherId);
    List<Customer> findAllCustomers();
}
