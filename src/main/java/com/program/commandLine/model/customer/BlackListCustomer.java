package com.program.commandLine.model.customer;

import com.program.commandLine.model.VoucherWallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class BlackListCustomer implements Customer {
    private final UUID customerId;
    private String name;
    private final String email;
    private LocalDateTime lastLoginAt;

    private final Logger logger = LoggerFactory.getLogger(BlackListCustomer.class);

    public BlackListCustomer(UUID customerId, String name, String email) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public BlackListCustomer(UUID customerId, String name, String email, LocalDateTime lastLoginAt) {
        validateName(name);
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.lastLoginAt = lastLoginAt;
    }


    @Override
    public List<VoucherWallet> getVoucherWallets() {
        return null;
    }

    @Override
    public CustomerType getCustomerType() {
        return CustomerType.BLACK_LIST_CUSTOMER;
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

    public void login() {
        logger.warn("블랙 리스트 고객이 로그인을 시도하였습니다.");
        throw new RuntimeException("! 블랙 리스트 고객은 로그인 할수없습니다.");
    }
}
