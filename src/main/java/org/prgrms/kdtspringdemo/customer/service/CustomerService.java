package org.prgrms.kdtspringdemo.customer.service;

import org.prgrms.kdtspringdemo.customer.domain.Customer;
import org.prgrms.kdtspringdemo.customer.repository.CustomerRepository;
import org.prgrms.kdtspringdemo.dto.CustomerRequestDto;
import org.prgrms.kdtspringdemo.dto.CustomerViewDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public CustomerViewDto insert(CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.insert(new Customer(UUID.randomUUID(), customerRequestDto.getName(), customerRequestDto.isBlack()));
        return new CustomerViewDto(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<CustomerViewDto> getCustomerViewDtoLists() {
        List<Customer> customerList = this.findAll();
        return customerList.stream().map(CustomerViewDto::new).toList();
    }

    public List<CustomerViewDto> findNoneHaveWalletCustomer() {
        return customerRepository.findNotHaveWalletCustomers().stream().map(CustomerViewDto::new).toList();
    }

    public List<CustomerViewDto> getBlackListCustomers() {
        return customerRepository.getAllBlackList().stream().map(CustomerViewDto::new).toList();
    }

    public void deleteById(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}
