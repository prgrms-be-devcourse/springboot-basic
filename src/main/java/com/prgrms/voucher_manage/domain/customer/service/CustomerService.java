package com.prgrms.voucher_manage.domain.customer.service;

import com.prgrms.voucher_manage.domain.customer.controller.dto.CreateCustomerReq;
import com.prgrms.voucher_manage.domain.customer.controller.dto.UpdateCustomerReq;
import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.repository.JdbcCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.BLACK;
import static com.prgrms.voucher_manage.base.ErrorMessage.*;

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
        List<Customer> customers = repository.findByType(BLACK.getData());
        if (customers.isEmpty()) {
            throw new RuntimeException(BLACK_CUSTOMER_NOT_EXIST.getMessage());
        }
        return repository.findByType(BLACK.getData());
    }

    public Customer save(CreateCustomerReq dto) {
        return repository.save(new Customer(dto.name(), dto.type()));
    }

    public void update(UUID customerId, UpdateCustomerReq dto) {
        Customer customer = getCustomerIfExists(customerId);
        repository.update(new Customer(customerId, dto.name(), customer.getType()));
    }

    public Customer getCustomerIfExists(UUID customerId){
        Customer customer = repository.findById(customerId);
        if (customer == null){
            throw new RuntimeException(CUSTOMER_NOT_EXIST.getMessage());
        }
        return customer;
    }

    public Customer findByName(String name) {
        return repository.findByName(name);
    }

    public Customer findById(UUID voucherId) {
        return repository.findById(voucherId);
    }
}
