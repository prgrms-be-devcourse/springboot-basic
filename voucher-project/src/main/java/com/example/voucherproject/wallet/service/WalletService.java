package com.example.voucherproject.wallet.service;

import com.example.voucherproject.user.model.User;
import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.wallet.model.Wallet;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface WalletService {

    List<Wallet> findAll();

    Map<UUID, User> findUserMap();

    Map<UUID, Voucher> findVoucherMap();

    Optional<Wallet> findById(UUID id);

    boolean create(UUID userId, UUID voucherId);

    boolean deleteById(UUID id);

    List<User> findAllUsers();

    List<Voucher> findAllVouchers();
}
