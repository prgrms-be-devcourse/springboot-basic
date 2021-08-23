package org.prgrms.kdt.blackCustomer.service;

import org.prgrms.kdt.blackCustomer.domain.BlackCustomer;
import org.prgrms.kdt.blackCustomer.repository.FileBlackCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlackCustomerService {

    private final FileBlackCustomerRepository customerRepository;

    public BlackCustomerService(FileBlackCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<BlackCustomer> getAllBlackList() {
        return customerRepository.findAll();
    }
}
