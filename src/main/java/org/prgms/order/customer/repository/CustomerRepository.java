package org.prgms.order.customer.repository;

import org.prgms.order.customer.entity.Customer;

import java.util.List;

public interface CustomerRepository {
//    Customer insert(Customer customer);
//    Customer update(Customer customer);
//
//    //Customer save(Customer customer);로 묶어서 표현하기도 한다.
//
//    int count();
//
//    List<Customer> findAll();
//
//    Optional<Customer> findById(UUID customerId);
//    Optional<Customer> findByName(String name);
//    Optional<Customer> findByEmail(String email);

    List<Customer> findBlackList();
//    void deleteAll();
}

