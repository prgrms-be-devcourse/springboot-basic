package com.program.commandLine.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Customer {

    default void validateName(String name){
        if(name.isBlank()){
            throw new IllegalArgumentException("! 이름을 입력하세요");
        }
    }

    CustomerType getCustomerType();

    UUID getCustomerId();

    String getName();

    String getEmail();

    LocalDateTime getLastLoginAt();

    void login() ;
}
