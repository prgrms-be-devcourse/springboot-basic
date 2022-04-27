package com.example.voucherproject.wallet.service;

import com.example.voucherproject.user.model.User;
import com.example.voucherproject.user.model.UserDTO;
import com.example.voucherproject.user.service.UserService;
import com.example.voucherproject.voucher.model.Voucher;
import com.example.voucherproject.voucher.service.VoucherService;
import com.example.voucherproject.wallet.model.Wallet;
import com.example.voucherproject.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalletWebService implements WalletService{

    private final WalletRepository walletRepository;
    private final UserService userService;
    private final VoucherService voucherService;

    @Override
    public List<Wallet> findAll() {
        var wallets =walletRepository.findAll();
        wallets.sort(Comparator.comparing(Wallet::getCreatedAt));
        return wallets;
    }

    @Override
    public List<User> findAllUsers() {
        return userService.findAll();
    }
    @Override
    public List<Voucher> findAllVouchers() {
        return voucherService.findAll();
    }

    @Override
    public Map<UUID, User> findUserMap() {
        Map<UUID ,User> userMap = new HashMap<>();
        var users = userService.findAll();
        users.forEach(user -> userMap.put(user.getId(), user));
        return userMap;
    }

    @Override
    public Map<UUID, Voucher> findVoucherMap() {
        Map<UUID,Voucher> voucherMap = new HashMap<>();
        var vouchers = voucherService.findAll();
        vouchers.forEach(voucher->voucherMap.put(voucher.getId(), voucher));
        return voucherMap;
    }

    @Override
    public Optional<Wallet> findById(UUID id) {
        return walletRepository.findById(id);
    }

    @Override
    @Transactional
    public boolean create(UUID userId, UUID voucherId) {
        var newWallet = Wallet.create(userId, voucherId);
        walletRepository.insert(newWallet);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteById(UUID id) {
        walletRepository.deleteById(id);
        return true;
    }

}