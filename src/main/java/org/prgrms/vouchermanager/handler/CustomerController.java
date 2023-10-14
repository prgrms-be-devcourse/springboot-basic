package org.prgrms.vouchermanager.handler;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.Repository.CustomerRepositroy;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepositroy repository;
    public List<Customer> findAll() {
        return repository.findAll();
    }
}
