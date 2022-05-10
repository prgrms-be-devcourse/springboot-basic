package com.example.voucher.controller.api;

import com.example.voucher.domain.voucher.VoucherType;
import com.example.voucher.dto.VoucherRequest;
import com.example.voucher.dto.VoucherResponse;
import com.example.voucher.service.voucher.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {
	private final VoucherService voucherService;

	public VoucherRestController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping
	public ResponseEntity<List<VoucherResponse>> findAll(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAt, @RequestParam @Nullable VoucherType voucherType) {
		if (createdAt != null) {
			return ResponseEntity.ok(voucherService.findByCreatedAt(createdAt).stream()
					.map((v) -> VoucherResponse.from(v))
					.collect(Collectors.toList()));
		}

		if (voucherType != null) {
			return ResponseEntity.ok(voucherService.findByVoucherType(voucherType).stream()
					.map((v) -> VoucherResponse.from(v))
					.collect(Collectors.toList()));
		}

		return ResponseEntity.ok(voucherService.findAll().stream()
		                                 .map((v) -> VoucherResponse.from(v))
		                                 .collect(Collectors.toList()));
	}

	@PostMapping
	public ResponseEntity<VoucherResponse> save(@RequestBody @Valid VoucherRequest voucherRequest) {
		return ResponseEntity.ok(VoucherResponse.from(
					voucherService.save(voucherRequest.getVoucherType(), voucherRequest.getDiscountAmount())));
	}

	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable @NonNull Long id) {
		voucherService.deleteById(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity<VoucherResponse> findById(@PathVariable @NonNull Long id) {
		return ResponseEntity.ok(VoucherResponse.from(
				voucherService.findById(id)));
	}
}
