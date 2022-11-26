package com.example.springbootbasic.controller.customer;

import com.example.springbootbasic.controller.dto.customer.CustomerDto;
import com.example.springbootbasic.controller.request.CreateCustomerRequest;
import com.example.springbootbasic.controller.response.ResponseBody;
import com.example.springbootbasic.domain.customer.Customer;
import com.example.springbootbasic.domain.customer.CustomerStatus;
import com.example.springbootbasic.service.customer.JdbcCustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class RestCustomerController {

    private final JdbcCustomerService customerService;

    public RestCustomerController(JdbcCustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseBody<List<CustomerDto>> allCustomers() {
        List<CustomerDto> findCustomers = customerService.findAllCustomers()
                .stream()
                .map(CustomerDto::newInstance)
                .toList();
        return ResponseBody.success(findCustomers);
    }

    @GetMapping("/vouchers")
    public ResponseBody<List<CustomerDto>> allCustomersWithVouchers() {
        List<Customer> customers = customerService.findAllCustomers();
        customers.forEach(customer -> customerService.findVouchersByCustomerId(customer.getCustomerId())
                .forEach(customer::receiveFrom));
        List<CustomerDto> findCustomers = customers.stream()
                .map(CustomerDto::newInstance)
                .toList();
        return ResponseBody.success(findCustomers);
    }

    @PostMapping("/add")
    public ResponseBody<CustomerDto> customerAddForm(CreateCustomerRequest request) {
        CustomerStatus customerStatus = CustomerStatus.of(request.status());
        CustomerDto savedCustomer = CustomerDto.newInstance(customerService.saveCustomer(new Customer(customerStatus)));
        if (savedCustomer.isEmpty()) {
            return ResponseBody.fail(savedCustomer);
        }
        return ResponseBody.success(savedCustomer);
    }

}
