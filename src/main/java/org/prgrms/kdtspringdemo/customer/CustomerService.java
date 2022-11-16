package org.prgrms.kdtspringdemo.customer;

import org.prgrms.kdtspringdemo.customer.repository.FileBlackCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final FileBlackCustomerRepository fileBlackCustomerRepository;

    public CustomerService(FileBlackCustomerRepository fileBlackCustomerRepository) {
        this.fileBlackCustomerRepository = fileBlackCustomerRepository;
    }

    public List<Customer> getAllBlackList() {
        return fileBlackCustomerRepository.findAllBlackCustomer();
    }
}
