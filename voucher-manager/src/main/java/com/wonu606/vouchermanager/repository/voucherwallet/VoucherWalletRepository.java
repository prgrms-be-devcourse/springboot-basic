package com.wonu606.vouchermanager.repository.voucherwallet;

import com.wonu606.vouchermanager.domain.voucherwallet.VoucherWallet;
import com.wonu606.vouchermanager.domain.customer.email.Email;
import java.util.List;
import java.util.UUID;

public interface VoucherWalletRepository {

    List<UUID> findOwnedVouchersByCustomer(Email email);

    void delete(VoucherWallet voucherWallet);

    VoucherWallet save(VoucherWallet voucherWallet);

    List<String> findOwnedCustomerByVoucher(UUID voucherId);
}
