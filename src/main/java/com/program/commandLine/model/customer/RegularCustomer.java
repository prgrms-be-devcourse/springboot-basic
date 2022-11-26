package com.program.commandLine.model.customer;

import com.program.commandLine.model.VoucherWallet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegularCustomer implements Customer {
    private final UUID customerId;
    private  final String name;
    private final String email;
    private LocalDateTime lastLoginAt;
    private final List<VoucherWallet> voucherWallets;

    public RegularCustomer(UUID customerId, String name, String email) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        voucherWallets = new ArrayList<>();
    }

    public RegularCustomer(UUID customerId, String name, String email, LocalDateTime lastLoginAt, List<VoucherWallet> voucherWallets) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
        this.voucherWallets = voucherWallets;
    }


    @Override
    public List<VoucherWallet> getVoucherWallets() {
        return voucherWallets;
    }

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.REGULAR_CUSTOMER;
    }

    @Override
    public UUID getCustomerId() {
        return customerId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public LocalDateTime getLastLoginAt() {
        return lastLoginAt;
    }


    @Override
    public void login(){
        lastLoginAt = LocalDateTime.now();
    }
}
