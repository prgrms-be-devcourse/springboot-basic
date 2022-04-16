package org.prgms.voucherProgram.service;

import java.util.List;

import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.dto.CustomerDto;
import org.prgms.voucherProgram.repository.customer.BlackListRepository;
import org.prgms.voucherProgram.repository.customer.JdbcCustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private static final String ERROR_DUPLICATE_CUSTOMER = "[ERROR] 이미 존재하는 고객입니다.";

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
            .ifPresent(c -> {
                throw new IllegalArgumentException(ERROR_DUPLICATE_CUSTOMER);
            });
    }

    public CustomerDto findByEmail(String email) {
        Customer customer = jdbcCustomerRepository.findByEmail(email)
            .orElseThrow(() -> {
                throw new IllegalArgumentException("[ERROR} 해당 이메일로 저장된 고객이 없습니다.");
            });

        return CustomerDto.from(customer);
    }

    public List<Customer> findBlackList() {
        return blackListRepository.findBlackCustomers();
    }
}
