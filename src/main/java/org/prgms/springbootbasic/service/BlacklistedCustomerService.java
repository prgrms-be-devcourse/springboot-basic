package org.prgms.springbootbasic.service;

import org.prgms.springbootbasic.domain.customer.BlacklistedCustomer;
import org.prgms.springbootbasic.repository.customer.BlackListedCustomerFileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlacklistedCustomerService {

    private final BlackListedCustomerFileRepository blackListedCustomerFileRepository;

    public BlacklistedCustomerService(BlackListedCustomerFileRepository blackListedCustomerFileRepository) {
        this.blackListedCustomerFileRepository = blackListedCustomerFileRepository;
    }

    public List<BlacklistedCustomer> findAll() {
        return blackListedCustomerFileRepository.findAll();
    }
}
