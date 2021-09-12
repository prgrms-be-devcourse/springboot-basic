package org.prgrms.kdt.service;


import org.prgrms.kdt.domain.CustomerDto;
import org.prgrms.kdt.domain.CustomerEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    public void createCustomers(List<CustomerEntity> customers);

    Optional<CustomerEntity> createCustomer(String email, String name);

    List<CustomerEntity> getAllCustomers();

    //없을 수도 있으니 Optional 로 처리한다.
    Optional<CustomerEntity> getCustomer(UUID customerId);

    void deleteCustomer(UUID customerId);

    Optional<CustomerEntity> updateCustomer(CustomerDto customer);
}
