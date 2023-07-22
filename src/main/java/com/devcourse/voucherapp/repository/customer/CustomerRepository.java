package com.devcourse.voucherapp.repository.customer;

import com.devcourse.voucherapp.entity.customer.Customer;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    Customer create(Customer customer);

    Optional<Customer> findCustomerByNickname(String nickname);

    List<Customer> findAllCustomers();

    Customer update(Customer customer);

    int deleteByNickname(String nickname);

    List<Customer> findBlackListCustomers();
}
