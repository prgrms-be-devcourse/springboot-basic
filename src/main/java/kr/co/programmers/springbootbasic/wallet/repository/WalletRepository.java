package kr.co.programmers.springbootbasic.wallet.repository;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.wallet.domain.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    void saveVoucherInCustomerWallet(UUID walletId, UUID voucherId);

    List<Wallet> findAllVouchersById(UUID walletId);

    Optional<Customer> findCustomerByVoucherId(UUID voucherId);

    void deleteByVoucherId(UUID voucherId);

    void handOverVoucherToCustomer(UUID voucherId, UUID walletId);
}
