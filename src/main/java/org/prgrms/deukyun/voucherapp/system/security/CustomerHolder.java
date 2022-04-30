package org.prgrms.deukyun.voucherapp.system.security;

import org.prgrms.deukyun.voucherapp.domain.customer.domain.Customer;

/**
 * 고객 홀더 <br>
 * - 로그인한 고객의 정보를 저장
 */
public class CustomerHolder {

    private static Customer customer;

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        CustomerHolder.customer = customer;
    }
}
