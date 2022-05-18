package com.voucher.vouchermanagement.controller.api.v1;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.voucher.vouchermanagement.controller.api.v1.request.UpdateVoucherRequest;
import com.voucher.vouchermanagement.dto.voucher.CreateVoucherRequest;
import com.voucher.vouchermanagement.dto.voucher.VoucherDto;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.service.voucher.VoucherService;

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
	public ResponseEntity<List<VoucherDto>> getByCriteria(
		@RequestParam(required = false) String voucherType,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime startAt,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS") LocalDateTime endAt
	) {
		if (startAt == null && endAt == null && voucherType == null) {
			return ResponseEntity.ok(this.voucherService.findAll());
		} else if (voucherType != null && startAt != null && endAt != null) {
			return ResponseEntity.ok(this.voucherService.findByTypeAndDate(VoucherType.fromString(voucherType), startAt, endAt));
		} else if (voucherType != null && startAt == null && endAt == null) {
			return ResponseEntity.ok(this.voucherService.findByType(VoucherType.fromString(voucherType)));
		} else if (voucherType == null && startAt != null && endAt != null) {
			return ResponseEntity.ok(this.voucherService.findByDate(startAt, endAt));
		} else {
			throw new IllegalArgumentException("잘못된 검색 파라미터 입니다.");
		}
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
