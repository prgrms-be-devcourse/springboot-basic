package com.prgrms.springbasic.domain.customer.repository;


import com.prgrms.springbasic.domain.customer.entity.Customer;

import java.util.List;

public interface CustomerRepository {

    Customer save(Customer customer);
    List<Customer> findAllBlackList() ;
}
