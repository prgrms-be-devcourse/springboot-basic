package org.prgrms.dev.wallet.repository;

import org.prgrms.dev.customer.domain.Customer;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.wallet.domain.Wallet;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository {
    List<Voucher> findVoucherByCustomerId(UUID customerId); // 고객이 할당받은 바우처 조회

    List<Voucher> findNoVoucherByCustomerId(UUID customerId); // 고객이 아직 할당받지 않은 바우처 조회

    List<Customer> findCustomerByVoucherId(UUID voucherId); // 특정 바우처를 보유한 고객 조회

    Optional<Wallet> findById(UUID walletId);

    int insert(Wallet wallet); // 고객한테 바우처 할당(지갑 생성)

    void deleteById(UUID walletId); // 고객이 보유한 바우처 삭제


}
