package com.prgrms.springbootbasic.repository.customer;

import com.prgrms.springbootbasic.domain.customer.Customer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {

    //생성(create)
    Customer save(Customer customer);

    //read-id
    Optional<Customer> findById(UUID customerId);

    //read-create at
    public List<Customer> findByCreatedAt();


    //조회(Read) - all
    List<Customer> findAll();

    //업데이트(update)
    void update(Customer customer);

    //delete-id
    int deleteById(UUID customerId);


    //customer 모두 삭제(Delete)
    void deleteAll();

    boolean checkCustomerId(UUID customerId);

}
