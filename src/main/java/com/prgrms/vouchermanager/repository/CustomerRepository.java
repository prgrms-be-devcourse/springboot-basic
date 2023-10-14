package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CustomerRepository {
    List<Customer> blacklist();
}
