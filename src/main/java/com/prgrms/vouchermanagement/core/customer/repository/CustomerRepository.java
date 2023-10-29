package com.prgrms.vouchermanagement.core.customer.repository;

import com.prgrms.vouchermanagement.core.customer.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {

    /**
     * 저장
     *
     * @param customer
     * @return
     */
    Customer save(Customer customer);

    /**
     * id로 조회
     *
     * @param id
     * @return
     */
    Optional<Customer> findById(String id);


    /**
     * 전체 조회
     *
     * @return
     */
    List<Customer> findAll();

    /**
     * id로 삭제
     *
     * @param id
     */
    void deleteById(String id);


    /**
     * 전체 삭제
     */
    void deleteAll();

}
