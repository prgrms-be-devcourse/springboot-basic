package org.prgrms.kdt.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.kdt.exception.ResourceNotFoundException;
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

    public CustomerDto getCustomerById(String customerId) {
        return customerRepository.findById(UUID.fromString(customerId))
                .map(CustomerMapper::customerToCustomerDto)
                .orElseThrow(() -> new ResourceNotFoundException("not found customerId : " + customerId));
    }

    public List<CustomerDto> getCustomers(String voucherId) {
        return customerRepository.findCustomersByVoucherId(UUID.fromString(voucherId)).stream()
                .map(CustomerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

}
