package kr.co.programmers.springbootbasic.wallet.repository;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.wallet.domain.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    Wallet saveVoucherInCustomerWallet(Voucher voucher);

    List<Wallet> findAllWalletByCustomerId(UUID customerId);

    Optional<Customer> findCustomerByVoucherId(UUID voucherId);

    void deleteByVoucherId(UUID voucherId);

    Customer handOverVoucherToCustomer(UUID voucherId, UUID customerId);
}
