package org.programmers.springbootbasic.domain.customer.service;

import org.programmers.springbootbasic.domain.customer.model.Customer;
import org.programmers.springbootbasic.domain.customer.dto.CustomerInsertDto;
import org.programmers.springbootbasic.domain.customer.dto.CustomerOutputDto;
import org.programmers.springbootbasic.domain.customer.repository.CustomerBlackListRepository;
import org.programmers.springbootbasic.domain.customer.repository.JdbcCustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerBlackListRepository customerBlackListRepository;
    private final JdbcCustomerRepository jdbcCustomerRepository;

    public CustomerService(CustomerBlackListRepository customerBlackListRepository, JdbcCustomerRepository jdbcCustomerRepository, JdbcCustomerRepository jdbcCustomerRepository1) {
        this.customerBlackListRepository = customerBlackListRepository;
        this.jdbcCustomerRepository = jdbcCustomerRepository1;
    }

    public List<CustomerOutputDto> collectBlacklists() {
        List<Customer> blacklists = customerBlackListRepository.findAll();
        return blacklists.stream()
                .map(black -> new CustomerOutputDto(black.getCustomerId(), black.getName()))
                .collect(Collectors.toList());
    }
}
