package org.programmers.customer.service;

import org.programmers.customer.model.BlackListCustomer;
import org.programmers.customer.repository.BlackListCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Profile("prod")
public class BlackListCustomerService {
    private final BlackListCustomerRepository blackListCustomerRepository;

    private static final Logger logger = LoggerFactory.getLogger(BlackListCustomerService.class);

    public BlackListCustomerService(BlackListCustomerRepository blackListCustomerRepository) {
        this.blackListCustomerRepository = blackListCustomerRepository;
    }

    public List<BlackListCustomer> getBlackList() throws IOException {
        return blackListCustomerRepository.findAllBlackList();
    }
}
