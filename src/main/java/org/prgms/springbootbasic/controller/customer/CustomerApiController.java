package org.prgms.springbootbasic.controller.customer;

import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.customer.CustomerRequestDto;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.prgms.springbootbasic.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.prgms.springbootbasic.common.CommonConstant.MAX_LOCAL_DATE_TIME;
import static org.prgms.springbootbasic.common.CommonConstant.MIN_LOCAL_DATE_TIME;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerApiController {
    private final CustomerService customerService;

    public CustomerApiController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> showCustomerBetweenDate(@RequestParam(required = false) String startDate,
                                                                  @RequestParam(required = false) String endDate) {
        LocalDateTime startOfDay = (startDate != null) ?
                LocalDate.parse(startDate).atStartOfDay() : MIN_LOCAL_DATE_TIME;
        LocalDateTime endOfDay = (endDate != null) ?
                LocalDate.parse(endDate).atTime(23, 59, 59) : MAX_LOCAL_DATE_TIME;

        return ResponseEntity.ok(customerService.findBetweenLocalDateTime(startOfDay, endOfDay));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> showDetails(@PathVariable String customerId) {
        Customer customer = customerService.findById(UUID.fromString(customerId))
                .orElseThrow(EntityNotFoundException::new);

        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CustomerRequestDto requestDto) {
        return ResponseEntity.ok(customerService.insert(requestDto.name(), requestDto.email()));
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String customerId) {
        UUID customerUUID = UUID.fromString(customerId);

        customerService.deleteById(customerUUID);
    }
}
