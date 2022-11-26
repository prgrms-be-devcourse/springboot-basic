package com.program.commandLine.model.customer;

import com.program.commandLine.model.VoucherWallet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class CustomerFactory {

    public Customer createCustomer(CustomerType customerType,UUID customerId, String name, String email){
        Customer newCustomer = switch (customerType){
            case REGULAR_CUSTOMER -> new RegularCustomer(customerId,name,email);
            case BLACK_LIST_CUSTOMER -> new BlackListCustomer(customerId,name,email);
        };
        return newCustomer;
    }

    public Customer createCustomer(CustomerType customerType, UUID customerId, String name, String email, LocalDateTime lastLoginAt, List<VoucherWallet> voucherWallet){
        Customer newCustomer = switch (customerType){
            case REGULAR_CUSTOMER -> new RegularCustomer(customerId,name,email,lastLoginAt, voucherWallet);
            case BLACK_LIST_CUSTOMER -> new BlackListCustomer(customerId,name,email,lastLoginAt);
        };
        return newCustomer;
    }

}
