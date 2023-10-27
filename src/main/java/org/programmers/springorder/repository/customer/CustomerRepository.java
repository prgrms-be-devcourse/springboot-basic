package org.programmers.springorder.repository.customer;

import org.programmers.springorder.model.customer.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface CustomerRepository {

    Customer save(Customer customer);   // 회원 저장

    List<Customer> findAll();   // 전체 회원 조회

    List<Customer> findAllBlackList();  // 블랙리스트 회원 조회

    Optional<Customer> findById(UUID customerId);  // 아이디로 회원 조회

    void deleteAll();   // 전체 회원 삭제
}
