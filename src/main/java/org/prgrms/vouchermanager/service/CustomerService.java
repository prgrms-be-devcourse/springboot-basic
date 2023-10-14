package org.prgrms.vouchermanager.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.Repository.CustomerRepositroy;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepositroy customerRepositroy;

    public List<Customer> findAll(){
        return customerRepositroy.findAll();
    }
}
