package com.program.commandLine.model.customer;

import com.program.commandLine.model.VoucherWallet;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface Customer {

    default void validateName(String name){
        if(name.isBlank()){
            throw new IllegalArgumentException("! 잘못된 이름입니다.");
        }
    }

    List<VoucherWallet> getVoucherWallets();

    CustomerType getCustomerType();

    UUID getCustomerId();

    String getName();

    String getEmail();

    LocalDateTime getLastLoginAt();

    void login() ;
}
