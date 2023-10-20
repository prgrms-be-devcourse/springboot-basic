package org.prgrms.prgrmsspring.repository.user;

import org.prgrms.prgrmsspring.entity.user.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository {
    List<Customer> findAll();

    List<Customer> findBlackAll();
}
