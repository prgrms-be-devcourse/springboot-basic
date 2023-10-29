package org.prgrms.kdt.customer;


import org.prgrms.kdt.customer.repository.CustomerRepository;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getBlackList() {
        return customerRepository.findBlackList();
    }

    public List<Voucher> getHaveVouchers(UUID customerId) {
        return customerRepository.findHaveVouchersById(customerId);
    }
}
