package org.prgrms.springbasic.service.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbasic.controller.view.dto.CustomerDto;
import org.prgrms.springbasic.domain.customer.Customer;
import org.prgrms.springbasic.repository.customer.CustomerRepository;
import org.prgrms.springbasic.utils.exception.DuplicatedDataException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.DUPLICATED_CUSTOMER;
import static org.prgrms.springbasic.utils.enumm.message.ErrorMessage.NOT_FOUND_CUSTOMER;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findCustomers() {
        return customerRepository.findCustomers();
    }

    public Customer findCustomerByCustomerId(UUID customerId) throws NoSuchElementException {
        return customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_CUSTOMER.getMessage()));
    }

    public void registerCustomer(Customer customer) {
        validateDuplicatedCustomer(customer.getCustomerId());

        customerRepository.save(customer);
    }

    public void modifyCustomer(Customer customer, CustomerDto customerDto) {
        var retrievedCustomer = customer.update(customerDto.getCustomerType(), customerDto.getName());

        customerRepository.update(retrievedCustomer);
    }

    public void removeCustomerById(UUID customerId) {
        customerRepository.deleteByCustomerId(customerId);
    }

    public void removeCustomers() {
        customerRepository.deleteCustomers();
    }

    private void validateDuplicatedCustomer(UUID customerId) {
        var retrievedCustomer = customerRepository.findByCustomerId(customerId);

        if (retrievedCustomer.isPresent()) {
            log.error("Got duplicated customer: {}", customerId);

            throw new DuplicatedDataException(DUPLICATED_CUSTOMER.getMessage());
        }
    }
}
