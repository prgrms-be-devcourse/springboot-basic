package kr.co.programmers.springbootbasic.customer.controller;

import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.customer.service.BlackCustomerService;
import kr.co.programmers.springbootbasic.customer.service.JdbcCustomerService;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class CustomerController {
    private final BlackCustomerService blackCustomerService;
    private final JdbcCustomerService jdbcCustomerService;

    public CustomerController(BlackCustomerService blackCustomerService, JdbcCustomerService jdbcCustomerService) {
        this.blackCustomerService = blackCustomerService;
        this.jdbcCustomerService = jdbcCustomerService;
    }

    @Transactional
    public CustomerResponse createCustomer(String customerName) {
        return jdbcCustomerService.createCustomer(customerName);
    }


    public Optional<CustomerResponse> findByCustomerId(UUID customerId) {
        return jdbcCustomerService.findByCustomerId(customerId);
    }

    public Optional<CustomerResponse> findCustomerById(UUID voucherId) {
        return jdbcCustomerService.findCustomerById(voucherId);
    }

    public List<CustomerResponse> findAllCustomers() {
        return jdbcCustomerService.findAllCustomer();
    }

    public List<CustomerResponse> findAllBlackCustomer() {
        return blackCustomerService.listAllBlackCustomer();
    }

    @Transactional
    public CustomerResponse updateCustomer(UUID customerId, CustomerStatus customerStatus) {
        return jdbcCustomerService.updateCustomer(customerId, customerStatus);
    }

    @Transactional
    public void deleteById(UUID customerId) {
        jdbcCustomerService.deleteById(customerId);
    }
}
