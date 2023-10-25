package org.prgms.kdtspringweek1.customer;

import org.prgms.kdtspringweek1.console.FindCustomerResponseDto;
import org.prgms.kdtspringweek1.customer.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<FindCustomerResponseDto> searchAllBlackCustomers() {
        return customerRepository.findAllBlackConsumer().stream()
                .map(FindCustomerResponseDto::new)
                .collect(Collectors.toList());
    }
}
