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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
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
        List<Customer> customers = customerService.findAllCustomer();
        List<CustomerDto> customerDtos = new ArrayList<>();
        customers.forEach(v -> customerDtos.add(entityToDto(v)));
        return ResponseEntity.ok()
                .headers(getHttpHeaders())
                .body(customerDtos);
    }

    @GetMapping("{customerId}")
    public ResponseEntity customer(@PathVariable UUID customerId) {
        Optional<Customer> customer = customerService.findCustomerById(customerId);
        if(customer.isPresent()) {
            return ResponseEntity.ok()
                    .headers(getHttpHeaders())
                    .body(entityToDto(customer.get()));
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    public ResponseEntity restAddCustomer(@RequestBody CustomerDto customerDto) {
        logger.info("save Customer {}", customerDto);
        customerService.createCustomer(customerDto.getName(), customerDto.getEmail());
        return ResponseEntity.created(URI.create("/api/customers/add")).build();
    }

    @PostMapping()
    public ResponseEntity restAddVoucher(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
        walletService.createWallet(customerId, voucherId);
        return ResponseEntity.created(URI.create("/api/customers/{customerId}/voucher/{voucherId}")).build();
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity restDeleteCustomer(@PathVariable UUID customerId) {
        Optional<Customer> deleteCustomer = customerService.findCustomerById(customerId);
        deleteCustomer.ifPresent(customerService::deleteCustomer);
        return ResponseEntity.noContent().build();
    }

    private static CustomerDto entityToDto(Customer customer) {
        return customer.toCustomerDto();
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        MediaType mediaType = new MediaType("application", "json", StandardCharsets.UTF_8);
        headers.setContentType(mediaType);
        return headers;
    }

}
