package org.prgrms.voucherapplication.service;

import org.prgrms.voucherapplication.entity.BlackListCustomer;
import org.prgrms.voucherapplication.repository.customer.FileBlackListCustomerRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * Black list Customer관련 service
 */
@Service
public class BlackListCustomerService {
    private FileBlackListCustomerRepository blackListCustomerRepository;

    public BlackListCustomerService(FileBlackListCustomerRepository repository) {
        this.blackListCustomerRepository = repository;
    }

    public List<BlackListCustomer> getAllBlackListCustomer() throws IOException {
        return blackListCustomerRepository.findAll();
    }
}
