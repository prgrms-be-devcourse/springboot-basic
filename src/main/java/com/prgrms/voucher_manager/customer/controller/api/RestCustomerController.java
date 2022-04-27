package com.prgrms.voucher_manager.customer.controller.api;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.controller.CustomerController;
import com.prgrms.voucher_manager.customer.controller.CustomerDto;
import com.prgrms.voucher_manager.customer.service.CustomerService;
import com.prgrms.voucher_manager.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/api/customers")
public class RestCustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final WalletService walletService;


    public RestCustomerController(CustomerService customerService, WalletService walletService) {
        this.customerService = customerService;
        this.walletService = walletService;
    }

    @GetMapping("")
    public List<CustomerDto> customers() {
        List<Customer> customers = customerService.findAllCustomer();
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.forEach(v -> customerDtos.add(entityToDto(v)));
        return customerDtos;
    }

    @GetMapping("{customerId}")
    public CustomerDto customer(@PathVariable UUID customerId) {
        Optional<Customer> customer = customerService.findCustomerById(customerId);
        return customer.map(RestCustomerController::entityToDto).orElse(null);
    }

    @PostMapping("/add")
    public CustomerDto restAddCustomer(@RequestBody CustomerDto customerDto) {
        logger.info("save Customer {}", customerDto);
        customerService.createCustomer(customerDto.getName(), customerDto.getEmail());
        return customerDto;
    }

    @PostMapping("/{customerId}/voucher/{voucherId}")
    public void restAddVoucher(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
        walletService.createWallet(customerId, voucherId);
    }

    @DeleteMapping("/{customerId}")
    public Customer restDeleteCustomer(@PathVariable UUID customerId) {
        Optional<Customer> deleteCustomer = customerService.findCustomerById(customerId);
        deleteCustomer.ifPresent(customerService::deleteCustomer);
        return deleteCustomer.get();
    }


    private static CustomerDto entityToDto(Customer customer) {
        return customer.toCustomerDto();
    }

}
