package com.prgrms.voucher_manage.domain.customer.service;

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
    private final JdbcCustomerRepository jdbcCustomerRepository;

    public List<Customer> getAllCustomers(){
        List<Customer> customers = jdbcCustomerRepository.findAll();
        if (customers.isEmpty()) {
            throw new  RuntimeException(CUSTOMER_NOT_EXIST.getMessage());
        }
        return customers;
    }

    public List<Customer> getBlackCustomers() {
        List<Customer> customers = jdbcCustomerRepository.findAll();
        if (customers.isEmpty()) {
            throw new  RuntimeException(BLACK_CUSTOMER_NOT_EXIST.getMessage());
        }
        return jdbcCustomerRepository.findByType(BLACK.getData());
    }

    public Customer save(Customer customer){
        return jdbcCustomerRepository.save(customer);
    }

    public void update(UpdateCustomerDto dto){
        int result = jdbcCustomerRepository.update(dto);
        if (result!=1){
            throw new RuntimeException(CUSTOMER_UPDATE_FAILED.getMessage());
        }
    }
    public Customer findByName(String name){
        return jdbcCustomerRepository.findByName(name).orElseThrow(RuntimeException::new);
    }

    public Customer findById(UUID id){
        return jdbcCustomerRepository.findById(id).orElseThrow(RuntimeException::new);
    }
}
