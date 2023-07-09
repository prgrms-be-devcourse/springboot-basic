package com.devcourse.voucherapp.repository;

import com.devcourse.voucherapp.entity.customer.Customer;
import java.util.Optional;

public interface CustomerRepository {

    Customer create(Customer customer);

    Optional<Customer> findCustomerByNickname(String nickname);
}
