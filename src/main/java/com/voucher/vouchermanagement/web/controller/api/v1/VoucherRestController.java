package com.voucher.vouchermanagement.web.controller.api.v1;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.voucher.vouchermanagement.domain.voucher.dto.CreateVoucherRequest;
import com.voucher.vouchermanagement.domain.voucher.dto.UpdateVoucherRequest;
import com.voucher.vouchermanagement.domain.voucher.dto.VoucherDto;
import com.voucher.vouchermanagement.domain.voucher.model.VoucherCriteria;
import com.voucher.vouchermanagement.domain.voucher.service.VoucherService;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

	private final VoucherService voucherService;

	public VoucherRestController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@PostMapping
	public ResponseEntity<UUID> create(@RequestBody CreateVoucherRequest request) {
		return ResponseEntity.ok(this.voucherService.create(request.getType(), request.getValue()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<VoucherDto> getById(@PathVariable("id") UUID id) {
		return ResponseEntity.ok(this.voucherService.findById(id));
	}

	@GetMapping
	public ResponseEntity<List<VoucherDto>> getByCriteria(VoucherCriteria criteria) {
		return ResponseEntity.ok(voucherService.findByCriteria(criteria));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable("id") UUID id) {
		this.voucherService.deleteById(id);

		return ResponseEntity.ok(id + "삭제 완료.");
	}

	@PatchMapping
	public ResponseEntity<VoucherDto> update(@RequestBody UpdateVoucherRequest request) {
		return ResponseEntity.ok(this.voucherService.update(request));
	}
}
