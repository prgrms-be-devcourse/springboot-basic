package org.programmers.springorder.customer.repository;

import org.programmers.springorder.customer.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerRepository {

    Customer save(Customer customer);   // 회원 저장

    List<Customer> findAll();   // 전체 회원 조회

    List<Customer> findAllBlackList();  // 블랙리스트 회원 조회

    Optional<Customer> findById(UUID customerId);  // 아이디로 회원 조회
}
