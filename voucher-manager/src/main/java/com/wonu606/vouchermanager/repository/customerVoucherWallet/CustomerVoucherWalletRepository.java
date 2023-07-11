package com.wonu606.vouchermanager.repository.customerVoucherWallet;

import com.wonu606.vouchermanager.domain.CustomerVoucherWallet.CustomerVoucherWallet;
import java.util.List;
import java.util.UUID;

public interface CustomerVoucherWalletRepository {

    List<UUID> findVoucherIdByCustomerEmailAddress(String emailAddress);

    void deleteByCustomerVoucherWallet(CustomerVoucherWallet customerVoucherWallet);

    CustomerVoucherWallet save(CustomerVoucherWallet customerVoucherWallet);

    List<String> findEmailAddressesByVoucherId(UUID voucherId);
}
