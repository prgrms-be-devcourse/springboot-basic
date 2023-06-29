package kr.co.programmers.springbootbasic.customer.impl;

import kr.co.programmers.springbootbasic.customer.Customer;
import kr.co.programmers.springbootbasic.customer.CustomerStatus;

public class CsvCustomer extends Customer {
    public CsvCustomer(long id, String name, CustomerStatus status) {
        super(id, name, status);
    }
}
