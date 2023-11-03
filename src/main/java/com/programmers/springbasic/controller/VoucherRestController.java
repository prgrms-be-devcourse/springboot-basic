package com.programmers.springbasic.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.springbasic.entity.voucher.VoucherType;
import com.programmers.springbasic.repository.dto.customer.CustomerResponse;
import com.programmers.springbasic.repository.dto.voucher.CreateVoucherRequest;
import com.programmers.springbasic.repository.dto.voucher.UpdateVoucherRequest;
import com.programmers.springbasic.repository.dto.voucher.VoucherResponse;
import com.programmers.springbasic.service.VoucherService;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {

	private final VoucherService voucherService;

	public VoucherRestController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@PostMapping
	public ResponseEntity<VoucherResponse> createVoucher(@Valid @RequestBody CreateVoucherRequest request) {
		VoucherResponse voucher = voucherService.createVoucher(request.voucherType(), request.discountValue());
		return new ResponseEntity<>(voucher, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<VoucherResponse>> getVouchers(
		@RequestParam(required = false) LocalDateTime startDate,
		@RequestParam(required = false) LocalDateTime endDate,
		@RequestParam(required = false) VoucherType voucherType) {
		List<VoucherResponse> vouchers = voucherService.getVouchers(startDate, endDate, voucherType);
		return ResponseEntity.ok(vouchers);
	}

	@GetMapping("/{voucherId}")
	public ResponseEntity<VoucherResponse> getVoucherDetail(@PathVariable UUID voucherId) {
		VoucherResponse voucher = voucherService.getVoucherDetail(voucherId);
		return ResponseEntity.ok(voucher);
	}

	@PutMapping("/{voucherId}")
	public ResponseEntity<VoucherResponse> updateVoucher(@PathVariable UUID voucherId,
		@Valid @RequestBody UpdateVoucherRequest request) {
		VoucherResponse updatedVoucher = voucherService.updateVoucher(voucherId, request.newDiscountValue());
		return ResponseEntity.ok(updatedVoucher);
	}

	@DeleteMapping("/{voucherId}")
	public ResponseEntity<Void> deleteVoucher(@PathVariable UUID voucherId) {
		voucherService.deleteVoucher(voucherId);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{voucherId}/customers")
	public ResponseEntity<List<CustomerResponse>> getCustomersByVoucher(@PathVariable UUID voucherId) {
		List<CustomerResponse> customers = voucherService.getCustomersByVoucher(voucherId);
		return ResponseEntity.ok(customers);
	}

}
