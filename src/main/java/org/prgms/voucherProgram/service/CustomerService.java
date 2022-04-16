package org.prgms.voucherProgram.service;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.domain.customer.Email;
import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.repository.customer.BlackListRepository;
import org.prgms.voucherProgram.repository.customer.JdbcCustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private static final String ERROR_CUSTOMER_IS_NOT_EXISTS_MESSAGE = "[ERROR] 해당 이메일로 저장된 고객이 없습니다.";
    private static final String ERROR_CUSTOMER_IS_EXISTS_MESSAGE = "[ERROR] 이미 해당 이메일로 저장된 고객이 있습니다.";
    private static final String ERROR_DUPLICATE_CUSTOMER_MESSAGE = "[ERROR] 이미 존재하는 고객입니다.";

    private final BlackListRepository blackListRepository;
    private final JdbcCustomerRepository jdbcCustomerRepository;

    public CustomerService(JdbcCustomerRepository jdbcCustomerRepository, BlackListRepository fileCustomerRepository) {
        this.jdbcCustomerRepository = jdbcCustomerRepository;
        this.blackListRepository = fileCustomerRepository;
    }

    public CustomerDto save(CustomerDto customerDto) {
        Customer customer = customerDto.toEntity();
        validateDuplicateCustomer(customer);
        return CustomerDto.from(jdbcCustomerRepository.save(customer));
    }

    private void validateDuplicateCustomer(Customer customer) {
        jdbcCustomerRepository.findByEmail(customer.getEmail())
            .ifPresent(duplicateCustomer -> {
                throw new IllegalArgumentException(ERROR_DUPLICATE_CUSTOMER_MESSAGE);
            });
    }

    public CustomerDto update(Email email, CustomerDto customerDto) {
        Customer customer = jdbcCustomerRepository.findByEmail(email.getEmail())
            .orElseThrow(() -> {
                throw new IllegalArgumentException(ERROR_CUSTOMER_IS_NOT_EXISTS_MESSAGE);
            });

        customer.changeName(customerDto.getName());
        customer.changeEmail(customerDto.getEmail());
        customer.login();

        jdbcCustomerRepository.findByEmail(customer.getEmail())
            .ifPresent(findCustomer -> validateDuplicateEmail(customer, findCustomer));

        return CustomerDto.from(jdbcCustomerRepository.update(customer));
    }

    private void validateDuplicateEmail(Customer customer, Customer findCustomer) {
        if (customer.isNotSameCustomer(findCustomer)) {
            throw new IllegalArgumentException(ERROR_CUSTOMER_IS_EXISTS_MESSAGE);
        }
    }

    public void delete(String email) {
        jdbcCustomerRepository.findByEmail(email).ifPresentOrElse(
            customer -> jdbcCustomerRepository.deleteByEmail(email),
            () -> {
                throw new IllegalArgumentException(ERROR_CUSTOMER_IS_NOT_EXISTS_MESSAGE);
            });
    }

    public CustomerDto findByEmail(Email email) {
        Customer customer = jdbcCustomerRepository.findByEmail(email.getEmail())
            .orElseThrow(() -> {
                throw new IllegalArgumentException(ERROR_CUSTOMER_IS_NOT_EXISTS_MESSAGE);
            });

        return CustomerDto.from(customer);
    }

    public List<CustomerDto> findCustomers() {
        return jdbcCustomerRepository.findAll()
            .stream()
            .map(CustomerDto::from)
            .collect(toList());
    }

    public List<Customer> findBlackList() {
        return blackListRepository.findBlackCustomers();
    }
}
