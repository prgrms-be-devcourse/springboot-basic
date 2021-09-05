package org.prgrms.kdt.customer;

import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 10:23 오전
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Optional<CustomerDto> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId)
                .map(CustomerMapper::customerToCustomerDto);
    }
}
