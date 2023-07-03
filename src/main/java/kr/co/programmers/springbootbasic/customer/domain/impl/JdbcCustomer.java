package kr.co.programmers.springbootbasic.customer.domain.impl;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;

import java.util.UUID;

public class JdbcCustomer extends Customer {

    public JdbcCustomer(UUID id, String name, CustomerStatus status, UUID walletId) {
        super(id, name, status, walletId);
    }
}
