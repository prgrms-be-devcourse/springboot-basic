package org.prgrms.kdt.BlackCustomer;

import org.prgrms.kdt.voucher.repository.FileBlackCustomerRepository;
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
