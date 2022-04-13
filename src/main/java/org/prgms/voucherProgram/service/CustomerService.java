package org.prgms.voucherProgram.service;

import java.util.List;

import org.prgms.voucherProgram.entity.customer.Customer;
import org.prgms.voucherProgram.repository.customer.BlackListRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final BlackListRepository blackListRepository;

    public CustomerService(BlackListRepository fileCustomerRepository) {
        this.blackListRepository = fileCustomerRepository;
    }

    public List<Customer> findBlackList() {
        return blackListRepository.findBlackCustomers();
    }
}
