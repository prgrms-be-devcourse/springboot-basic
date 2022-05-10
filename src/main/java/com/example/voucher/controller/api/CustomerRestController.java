package com.example.voucher.controller.api;

import com.example.voucher.domain.customer.Customer;
import com.example.voucher.dto.CustomerRequest;
import com.example.voucher.dto.CustomerResponse;
import com.example.voucher.service.customer.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

	private final CustomerService customerService;

	public CustomerRestController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid CustomerRequest customerRequest) {
		return ResponseEntity.ok(CustomerResponse.from(customerService.save(customerRequest.getName())));
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		List<Customer> customers = customerService.findAll();
		return ResponseEntity.ok(customers.stream()
				.map(CustomerResponse::from)
				.collect(Collectors.toList()));
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable @NotNull Long id) {
		customerService.deleteById(id);
	}
}
