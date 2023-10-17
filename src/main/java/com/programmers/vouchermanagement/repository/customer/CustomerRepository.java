package com.programmers.vouchermanagement.repository.customer;

import com.programmers.vouchermanagement.domain.customer.Customer;

import java.util.List;

public interface CustomerRepository {
    // 추후 고객 추가, 삭제, 수정 기능이 필요 시 추가
    List<Customer> findAllBannedCustomers();
}
