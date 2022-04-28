package org.prgrms.deukyun.voucherapp.util.security;

import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;

public class CustomerHolder {

    private static Customer customer;

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        CustomerHolder.customer = customer;
    }
}
