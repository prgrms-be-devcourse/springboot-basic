package kr.co.programmers.springbootbasic.customer.controller;

import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.customer.service.BlackCustomerService;
import kr.co.programmers.springbootbasic.customer.service.JdbcCustomerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Controller
@Profile("console")
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

    public Optional<CustomerResponse> findByCustomerId(String customerId) {
        return jdbcCustomerService.findByCustomerId(customerId);
    }

    public Optional<CustomerResponse> findByVoucherId(String voucherId) {
        return jdbcCustomerService.findByVoucherId(voucherId);
    }

    public List<CustomerResponse> findAllCustomers() {
        return jdbcCustomerService.findAllCustomer();
    }

    public List<CustomerResponse> findAllBlackCustomer() {
        return blackCustomerService.listAllBlackCustomer();
    }

    @Transactional
    public CustomerResponse updateCustomer(String customerId, CustomerStatus customerStatus) {
        return jdbcCustomerService.updateCustomer(customerId, customerStatus);
    }

    @Transactional
    public void deleteById(String customerId) {
        jdbcCustomerService.deleteById(customerId);
    }
}
