package org.prgrms.kdtspringhw.customer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository{
    Customer insert(Customer customer);

    Customer update(Customer customer);

    //Customer save(Customer customer); //있으면 수정하고 없으면 넣는 메소드

    int count();

    List<Customer> findAll();

    Optional<Customer> findById(UUID cutomerId);

    Optional<Customer> findByName(String name);

    Optional<Customer> findByEmail(String email);

    void deleteAll();
}
