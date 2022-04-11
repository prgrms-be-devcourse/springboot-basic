package com.prgrms.voucher_manager.customer;

import lombok.Builder;
import org.springframework.stereotype.Repository;

@Builder
public class BlackCustomer implements Customer{

    private final int id;
    private final String name;
    private String phoneNumber;

    public BlackCustomer(int id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String getInfo() {
        return "BlackList - id : " + id + ", name : " + name + ", phoneNumber : " + phoneNumber +"\n";
    }
}
