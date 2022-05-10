package com.prgrms.voucher_manager.customer.controller.api;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.controller.CustomerController;
import com.prgrms.voucher_manager.customer.controller.CustomerDto;
import com.prgrms.voucher_manager.customer.service.CustomerService;
import com.prgrms.voucher_manager.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@CrossOrigin(origins = "*")
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
    public ResponseEntity customers() {
        List<CustomerDto> customers = customerService.findAllCustomer();
        return ResponseEntity.ok()
                .body(customers);
    }

    @GetMapping("{customerId}")
    public ResponseEntity customer(@PathVariable UUID customerId) {
        CustomerDto customer = customerService.findCustomerById(customerId);
        return ResponseEntity.ok()
                .body(customer);
    }

    @PostMapping("")
    public ResponseEntity restAddCustomer(@RequestBody CustomerDto customerDto) {
        logger.info("save Customer {}", customerDto);
        customerService.createCustomer(customerDto.getName(), customerDto.getEmail());
        return ResponseEntity.created(URI.create("/api/customers/add")).build();
    }

    @PostMapping("/vouchers")
    public ResponseEntity restAddVoucher(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
        walletService.createWallet(customerId, voucherId);
        return ResponseEntity.created(URI.create("/api/customers/{customerId}/voucher/{voucherId}")).build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity restDeleteCustomer(@PathVariable UUID customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }
}
