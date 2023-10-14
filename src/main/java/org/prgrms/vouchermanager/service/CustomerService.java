package org.prgrms.vouchermanager.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.Repository.CustomerRepositroy;
import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepositroy customerRepositroy; //yaml 파일에서 Repository 지정

    public List<Customer> findAll(){
        return customerRepositroy.findAll();
    }

    public Customer createCustomer(String name){
        String customerId = String.valueOf(UUID.randomUUID());
        return customerRepositroy.save(new Customer(customerId, name));
    }
}
