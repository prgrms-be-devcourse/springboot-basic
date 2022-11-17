package com.program.commandLine.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public interface Customer {

    default void validateName(String name){
        if(name.isBlank()){
            throw new RuntimeException("Name should not be black");
        }
    }

    CustomerType getCustomerType();

    UUID getCustomerId();

    String getName();

    String getEmail();

    LocalDateTime getLastLoginAt();

}
