package com.example.voucher.controller.api;

import com.example.voucher.dto.VoucherRequest;
import com.example.voucher.dto.VoucherResponse;
import com.example.voucher.service.voucher.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {
	private final VoucherService voucherService;

	public VoucherRestController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	// 전체 조회 기능
	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(voucherService.findAll()
				                               .stream()
				                               .map((v) -> VoucherResponse.from(v))
				                               .collect(Collectors.toList()));
	}

	// 추가 기능
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid VoucherRequest voucherRequest) {
		return ResponseEntity.ok(VoucherResponse.from(
					voucherService.save(voucherRequest.getVoucherType(), voucherRequest.getDiscountAmount())));
	}
}
