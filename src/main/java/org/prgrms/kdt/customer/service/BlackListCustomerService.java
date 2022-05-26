package org.prgrms.kdt.customer.service;

import java.util.List;
import org.prgrms.kdt.customer.model.Customer;
import org.prgrms.kdt.customer.repository.BlackListRepository;
import org.springframework.stereotype.Service;

@Service
public class BlackListCustomerService {

    private final BlackListRepository blackListRepository;

    public BlackListCustomerService(BlackListRepository blackListRepository) {
        this.blackListRepository = blackListRepository;
    }

    public List<Customer> getAll() {
        return this.blackListRepository.findAll();
    }

}
