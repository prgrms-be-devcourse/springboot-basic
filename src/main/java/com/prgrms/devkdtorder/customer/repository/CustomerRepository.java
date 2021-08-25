package com.prgrms.devkdtorder.customer.repository;

import com.prgrms.devkdtorder.customer.domain.BlackCustomers;
import com.prgrms.devkdtorder.customer.domain.Customer;
import com.prgrms.devkdtorder.customer.domain.CustomerType;

import java.util.List;

public interface CustomerRepository {

    BlackCustomers findAllBlackCustomers();
}
