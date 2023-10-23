package org.prgrms.vouchermanager.repository.customer;

import org.prgrms.vouchermanager.domain.customer.Customer;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("dev")
public class MemoryCustomerRepository implements CustomerRepositroy{
    Map<UUID, Customer> blackList = new ConcurrentHashMap<>();

    @Override
    public List<Customer> findAll() {
        List<Customer> result = new ArrayList<>(blackList.values());
        return result;
    }

    public Customer save(Customer customer){
        blackList.put(customer.getCustomerId(), customer);
        return customer;
    }
}
