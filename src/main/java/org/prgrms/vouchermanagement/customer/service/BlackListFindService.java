package org.prgrms.vouchermanagement.customer.service;

import org.prgrms.vouchermanagement.customer.domain.Customer;
import org.prgrms.vouchermanagement.customer.repository.BlackListCustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackListFindService {

    private final BlackListCustomerRepository blackListCustomerRepository;

    @Autowired
    public BlackListFindService(BlackListCustomerRepository blackListCustomerRepository) {
        this.blackListCustomerRepository = blackListCustomerRepository;
    }

    public List<Customer> findAllBlackList() {
        return blackListCustomerRepository.findAll();
    }
}
