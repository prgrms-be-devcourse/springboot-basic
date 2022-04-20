package com.prgrms.voucher_manager.wallet.repository;

import com.prgrms.voucher_manager.wallet.Wallet;
import org.springframework.dao.DataAccessException;

import java.nio.ByteBuffer;
import java.util.*;

public interface WalletRepository {

    Wallet insert(Wallet wallet);

    Wallet update(Wallet wallet, UUID beforeId);

    Integer count();

    List<Wallet> findAll();

    List<Wallet> findByCustomerId(UUID customerId);

    List<Wallet> findByVoucherId(UUID voucherId);


    void deleteAll();

    void deleteByVoucherId(Wallet wallet);


}
