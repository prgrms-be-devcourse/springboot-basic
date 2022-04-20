package com.prgrms.voucher_manager.customer;

import com.prgrms.voucher_manager.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void findAll(){
        if(customerRepository.getSize() == 0) throw new IllegalArgumentException();
        customerRepository.findAll();
    }
}
