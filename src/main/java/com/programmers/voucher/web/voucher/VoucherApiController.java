package com.programmers.voucher.web.voucher;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.service.VoucherWebService;
import com.programmers.voucher.web.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.web.voucher.dto.VoucherResponseDto;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherApiController {

	private final VoucherWebService voucherWebService;

	public VoucherApiController(VoucherWebService voucherWebService) {
		this.voucherWebService = voucherWebService;
	}

	@PostMapping("/add")
	public VoucherResponseDto createVoucher(VoucherRequestDto voucherRequestDto) {
		return voucherWebService.createVoucher(voucherRequestDto);
	}

	@GetMapping("/{voucherId}")
	public VoucherResponseDto getVoucher(@PathVariable UUID voucherId) {
		return voucherWebService.findById(voucherId);
	}

	@GetMapping("/all")
	public List<VoucherResponseDto> getVouchers(
		@RequestParam(value = "type", required = false) VoucherType voucherType,
		@RequestParam(value = "start", required = false) LocalDateTime startTime,
		@RequestParam(value = "end", required = false) LocalDateTime endTime
	) {
		return voucherWebService.getVouchersBy(voucherType, startTime, endTime);
	}

	@DeleteMapping("/{voucherId}")
	public ResponseEntity removeVoucher(@PathVariable UUID voucherId) {
		voucherWebService.removeVoucher(voucherId);
		return ResponseEntity.noContent().build();
	}
}
