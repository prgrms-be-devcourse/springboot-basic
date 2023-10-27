package com.prgrms.voucher_manage.domain.customer.service;

import com.prgrms.voucher_manage.domain.customer.entity.Customer;
import com.prgrms.voucher_manage.domain.customer.repository.JdbcCustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.BLACK;
import static com.prgrms.voucher_manage.domain.customer.entity.CustomerType.NORMAL;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final JdbcCustomerRepository jdbcCustomerRepository;

    public List<Customer> getAllCustomers(){
        List<Customer> customers = jdbcCustomerRepository.findAll();
        if (customers.isEmpty()) {
            throw new  RuntimeException("There is no customers.");
        }
        return customers;
    }

    public List<Customer> getBlackCustomers() {
        return jdbcCustomerRepository.findByType(BLACK.getData());
    }

    public Customer save(Customer customer){
        return jdbcCustomerRepository.save(customer);
    }

    public void update(Customer customer){
        int result = jdbcCustomerRepository.update(customer);
        if (result!=1){
            throw new RuntimeException("Failed to update user");
        }
    }
    public Customer findByName(String name){
        return jdbcCustomerRepository.findByName(name).orElseThrow(RuntimeException::new);
    }

    public Customer findById(UUID id){
        return jdbcCustomerRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
