package com.prgms.vouchermanager.service.customer;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.dto.CreateCustomerDto;
import com.prgms.vouchermanager.dto.UpdateCustomerDto;
import com.prgms.vouchermanager.repository.customer.CustomerRepository;
import com.prgms.vouchermanager.validation.InputValidation;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.prgms.vouchermanager.exception.ExceptionType.*;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final InputValidation inputValidation;

    public CustomerService(CustomerRepository customerRepository, InputValidation inputValidation) {
        this.customerRepository = customerRepository;
        this.inputValidation = inputValidation;
    }

    @Transactional
    public Customer create(CreateCustomerDto dto) {

        boolean blackList = false;

        if (inputValidation.validCustomerInfo(dto.getName(), dto.getEmail(), dto.getBlackList())) {

            if (dto.getBlackList() == 1) {
                blackList = true;
            }

            return customerRepository.save(new Customer(null, dto.getName(), dto.getEmail(), blackList));
        }
        throw new RuntimeException(INVALID_CUSTOMER_INFO.getMessage());

    }

    @Transactional
    public void update(Long id, UpdateCustomerDto dto) {

        boolean blackList = false;
        if (inputValidation.validCustomerInfo(dto.getName(), dto.getEmail(), dto.getBlackList())) {
            if (dto.getBlackList() == 1) {
                blackList = true;
            }
            customerRepository.update(new Customer(id, dto.getName(), dto.getEmail(), blackList));
        }
    }

    public Customer findById(Long id) {

        return customerRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(INVALID_CUSTOMER_ID.getMessage(),1));

    }

    public List<Customer> findAll() {

        List<Customer> customers = customerRepository.findAll();
        return customers;
    }

    @Transactional
    public void deleteById(Long id) {

        customerRepository.deleteById(id);

    }

    @Transactional
    public void deleteAll() {

        customerRepository.deleteAll();

    }

    public List<Customer> findBlackList() {

        return customerRepository.findBlackList();

    }
}
