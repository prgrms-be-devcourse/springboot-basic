package kr.co.programmers.springbootbasic.customer.domain.impl;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;

import java.util.UUID;

public class CsvCustomer extends Customer {
    public CsvCustomer(UUID id, String name, CustomerStatus status) {
        super(id, name, status);
    }
}
