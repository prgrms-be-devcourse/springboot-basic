package kr.co.programmers.springbootbasic.customer.repository;

import kr.co.programmers.springbootbasic.customer.domain.Customer;

import java.util.List;

public interface CustomerRepository {
    List<Customer> listAllBlackCustomer();
}
