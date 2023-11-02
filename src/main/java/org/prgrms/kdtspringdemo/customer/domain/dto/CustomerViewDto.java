package org.prgrms.kdtspringdemo.customer.domain.dto;

import org.prgrms.kdtspringdemo.customer.domain.Customer;

import java.util.UUID;

public class CustomerViewDto {
    private UUID customerId;
    private String name;
    private boolean isBlack;

    public CustomerViewDto(UUID customerId, String name, boolean isBlack) {
        this.customerId = customerId;
        this.name = name;
        this.isBlack = isBlack;
    }

    public CustomerViewDto(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.name = customer.getName();
        this.isBlack = customer.isBlack();
    }

    @Override
    public String toString() {
        return "customerId =" + customerId +
                " / name ='" + name + '\'' +
                " / isBlack =" + isBlack;
    }

    public UUID getCustomerId() {
        return customerId;
    }

    public void setCustomerId(UUID customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }
}
