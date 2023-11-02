package com.prgrms.voucher_manage.domain.customer.service;

import com.prgrms.voucher_manage.domain.customer.controller.dto.CreateCustomerDto;
import com.prgrms.voucher_manage.domain.customer.dto.UpdateCustomerDto;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.repository.JdbcCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.BLACK;
import static com.prgrms.voucher_manage.exception.ErrorMessage.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final JdbcCustomerRepository repository;

    public List<Customer> getAllCustomers() {
        List<Customer> customers = repository.findAll();
        if (customers.isEmpty()) {
            throw new RuntimeException(CUSTOMER_NOT_EXIST.getMessage());
        }
        return customers;
    }

    public List<Customer> getBlackCustomers() {
        List<Customer> customers = repository.findAll();
        if (customers.isEmpty()) {
            throw new RuntimeException(BLACK_CUSTOMER_NOT_EXIST.getMessage());
        }
        return repository.findByType(BLACK.getData());
    }

    public Customer save(CreateCustomerDto dto) {
        return repository.save(new Customer(dto.name(), dto.type()));
    }

    public void update(UpdateCustomerDto dto) {
        repository.update(dto);
    }

    public Customer findByName(String name) {
        return repository
                .findByName(name)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_NOT_EXIST.getMessage()));
    }

    public Customer findById(UUID voucherId) {
        return repository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(CUSTOMER_NOT_EXIST.getMessage()));
    }
}
