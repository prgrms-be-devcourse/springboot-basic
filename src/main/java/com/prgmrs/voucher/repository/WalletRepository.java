package com.prgmrs.voucher.repository;

import com.prgmrs.voucher.model.Wallet;

public interface WalletRepository {
    void save(Wallet wallet);

    void free(Wallet wallet);
}
