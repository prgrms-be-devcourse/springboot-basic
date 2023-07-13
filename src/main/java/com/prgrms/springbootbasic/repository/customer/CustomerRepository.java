package com.prgrms.springbootbasic.repository.customer;

import com.prgrms.springbootbasic.domain.customer.Customer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    //생성(create)
    Customer save(Customer customer);

    //read-id
    Optional<Customer> findById(UUID customerId);

    //read-create at
    Optional<Customer> findByCreateAt(LocalDateTime createAt);

    //조회(Read) - all
    List<Customer> findAll();

    //업데이트(update)
    Optional<Customer> update(Customer customer);

    //delete-id
    Optional<Customer> deleteById(UUID customerId);


    //customer 모두 삭제(Delete)
    void deleteAll();
}
