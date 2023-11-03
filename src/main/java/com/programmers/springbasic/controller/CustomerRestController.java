package com.programmers.springbasic.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.springbasic.repository.dto.customer.CreateCustomerRequest;
import com.programmers.springbasic.repository.dto.customer.CustomerResponse;
import com.programmers.springbasic.repository.dto.customer.UpdateCustomerRequest;
import com.programmers.springbasic.repository.dto.voucher.VoucherResponse;
import com.programmers.springbasic.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerRestController {

	private final CustomerService customerService;

	public CustomerRestController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping("/blacklist")
	public ResponseEntity<List<CustomerResponse>> getBlacklistCustomers() {
		List<CustomerResponse> blacklistCustomers = customerService.getBlacklistCustomers();
		return ResponseEntity.ok(blacklistCustomers);
	}

	@PostMapping
	public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
		CustomerResponse customerResponse = customerService.createCustomer(request.name(), request.name());
		return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
		List<CustomerResponse> allCustomers = customerService.getAllCustomers();
		return ResponseEntity.ok(allCustomers);
	}

	@GetMapping("/{customerId}")
	public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable UUID customerId) {
		CustomerResponse customerResponse = customerService.getCustomerById(customerId);
		return ResponseEntity.ok(customerResponse);
	}

	@PatchMapping("/{customerId}")
	public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID customerId,
		@Valid @RequestBody UpdateCustomerRequest request) {
		CustomerResponse updatedCustomer = customerService.updateCustomer(customerId, request.nameToUpdate());
		return ResponseEntity.ok(updatedCustomer);
	}

	@DeleteMapping("/{customerId}")
	public ResponseEntity<Void> removeCustomer(@PathVariable UUID customerId) {
		customerService.removeCustomer(customerId);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/{customerId}/vouchers/{voucherId}")
	public ResponseEntity<Void> assignVoucherToCustomer(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
		customerService.assignVoucherToCustomer(customerId, voucherId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{customerId}/vouchers")
	public ResponseEntity<List<VoucherResponse>> getVouchersByCustomer(@PathVariable UUID customerId) {
		List<VoucherResponse> vouchers = customerService.getVouchersByCustomer(customerId);
		return ResponseEntity.ok(vouchers);
	}

	@DeleteMapping("/{customerId}/vouchers/{voucherId}")
	public ResponseEntity<Void> removeVoucherFromCustomer(@PathVariable UUID customerId, @PathVariable UUID voucherId) {
		customerService.removeVoucherFromCustomer(customerId, voucherId);
		return ResponseEntity.noContent().build();
	}

}
