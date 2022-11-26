package com.program.commandLine.repository;

import com.program.commandLine.model.voucher.Voucher;

import java.util.List;
import java.util.UUID;

public interface VoucherWalletRepository {

    void createWallet(UUID voucherId, UUID customerId);

    void deleteWallet(UUID voucherId);

    List<Voucher> findNotIncludeWallet();

    UUID findCustomerWalletByVoucher(UUID voucherId);

}
