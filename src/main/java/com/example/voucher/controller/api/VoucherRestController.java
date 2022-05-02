package com.example.voucher.controller.api;

import com.example.voucher.domain.voucher.Voucher;
import com.example.voucher.dto.VoucherRequest;
import com.example.voucher.dto.VoucherResponse;
import com.example.voucher.dto.VoucherSearchRequest;
import com.example.voucher.service.voucher.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {
	private final VoucherService voucherService;

	public VoucherRestController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	// 조회 기능
	@GetMapping
	public ResponseEntity<?> findAll(@RequestBody @Nullable VoucherSearchRequest voucherSearchRequest) {
		List<Voucher> vouchers = new ArrayList<>();

		// 전체 조회
		if (voucherSearchRequest == null || (voucherSearchRequest.getCreatedAt() == null && voucherSearchRequest.getVoucherType() == null)) {
			vouchers = voucherService.findAll();
		}

		// 생성 기간 조회
		else if (voucherSearchRequest.getCreatedAt() != null) {
			vouchers = voucherService.findByCreatedAt(voucherSearchRequest.getCreatedAt());
		}

		// 바우처 타입 조회
		else if (voucherSearchRequest.getVoucherType() != null) {
			vouchers = voucherService.findByVoucherType(voucherSearchRequest.getVoucherType());
		}

		return ResponseEntity.ok(vouchers.stream()
		                                 .map((v) -> VoucherResponse.from(v))
		                                 .collect(Collectors.toList()));
	}

	// 추가 기능
	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid VoucherRequest voucherRequest) {
		return ResponseEntity.ok(VoucherResponse.from(
					voucherService.save(voucherRequest.getVoucherType(), voucherRequest.getDiscountAmount())));
	}

	// 삭제 기능
	@DeleteMapping
	public void deleteById(@RequestBody @NonNull Long voucherId) {
		voucherService.deleteById(voucherId);
	}

	// 아이디로 조회 기능
	@GetMapping("/{voucherId}")
	public ResponseEntity<?> findById(@PathVariable @NonNull Long voucherId) {
		return ResponseEntity.ok(voucherService.findById(voucherId));
	}
}
