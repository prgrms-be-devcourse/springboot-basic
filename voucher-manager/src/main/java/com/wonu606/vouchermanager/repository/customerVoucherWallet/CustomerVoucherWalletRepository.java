package com.wonu606.vouchermanager.repository.customerVoucherWallet;

import com.wonu606.vouchermanager.domain.CustomerVoucherWallet.CustomerVoucherWallet;
import com.wonu606.vouchermanager.domain.CustomerVoucherWallet.CustomerVoucherWalletResultSet;
import java.util.List;
import java.util.UUID;

public interface CustomerVoucherWalletRepository {

    List<UUID> findIdByCustomerEmailAddress(String emailAddress);

    void deleteByWallet(CustomerVoucherWallet customerVoucherWallet);

    CustomerVoucherWallet save(CustomerVoucherWallet customerVoucherWallet);

    List<String> findEmailAddressesByVoucherId(UUID voucherId);
}
