package org.prgrms.voucherapplication.entity;

public record BlackListCustomer(long id, String name) implements Customer {
    @Override
    public long getCustomerId() {
        return id;
    }

    @Override
    public String getCustomerName() {
        return name;
    }

}
