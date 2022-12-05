package org.prgrms.springorder.domain.voucher_wallet.repository;

import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.voucher_wallet.model.Wallet;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher_wallet.model.VoucherWallet;

public interface VoucherWalletRepository {

    VoucherWallet insert(VoucherWallet voucherWallet);

    Optional<Wallet> findAllWithCustomerAndVoucher(UUID customerId);

    void deleteByCustomerId(UUID customerId);

    Optional<CustomerWithVoucher> findByVoucherIdWithCustomer(UUID voucherId);

    void deleteByCustomerIdAndVoucherID(UUID customerId, UUID voucherId);

    Optional<VoucherWallet> findByCustomerIdAndVoucherId(UUID customerId, UUID voucherId);

    boolean existsByCustomerIdAndVoucherId(UUID customerId, UUID voucherId);
}
