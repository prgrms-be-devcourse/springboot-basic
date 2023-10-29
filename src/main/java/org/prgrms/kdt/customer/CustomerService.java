package org.prgrms.kdt.customer;


import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getBlackList() {
        return customerRepository.findBlackList();
    }

    public List<Voucher> getAllVoucher() {
        return customerRepository.findAllVoucher();
    }
}
