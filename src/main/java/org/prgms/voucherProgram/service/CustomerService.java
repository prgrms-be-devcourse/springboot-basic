package org.prgms.voucherProgram.service;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.domain.customer.Email;
import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.exception.CustomerIsNotExistsException;
import org.prgms.voucherProgram.exception.DuplicateEmailException;
import org.prgms.voucherProgram.repository.customer.BlackListRepository;
import org.prgms.voucherProgram.repository.customer.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final BlackListRepository blackListRepository;
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository, BlackListRepository fileCustomerRepository) {
        this.customerRepository = customerRepository;
        this.blackListRepository = fileCustomerRepository;
    }

    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = customerDto.toEntity();
        validateDuplicateCustomer(customer);
        return CustomerDto.from(customerRepository.save(customer));
    }

    private void validateDuplicateCustomer(Customer customer) {
        customerRepository.findByEmail(customer.getEmail()).ifPresent(duplicateCustomer -> {
            throw new DuplicateEmailException();
        });
    }

    public CustomerDto update(Email email, CustomerDto customerDto) {
        Customer customer = customerDto.toEntity();

        customerRepository.findByEmail(email.getEmail()).orElseThrow(() -> {
            throw new CustomerIsNotExistsException();
        });

        customerRepository.findByEmail(customer.getEmail())
            .ifPresent(findCustomer -> validateDuplicateEmail(customer, findCustomer));

        return CustomerDto.from(customerRepository.update(customer));
    }

    private void validateDuplicateEmail(Customer customer, Customer findCustomer) {
        if (customer.isNotSameCustomer(findCustomer)) {
            throw new DuplicateEmailException();
        }
    }

    public void delete(Email email) {
        customerRepository.findByEmail(email.getEmail())
            .ifPresentOrElse(customer -> customerRepository.deleteByEmail(email.getEmail()), () -> {
                throw new CustomerIsNotExistsException();
            });
    }

    public CustomerDto findByEmail(Email email) {
        Customer customer = customerRepository.findByEmail(email.getEmail()).orElseThrow(() -> {
            throw new CustomerIsNotExistsException();
        });

        return CustomerDto.from(customer);
    }

    public List<CustomerDto> findCustomers() {
        return customerRepository.findAll()
            .stream()
            .map(CustomerDto::from)
            .collect(toList());
    }

    public List<Customer> findBlackList() {
        return blackListRepository.findBlackCustomers();
    }
}
