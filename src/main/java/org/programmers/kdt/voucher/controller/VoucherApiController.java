package org.programmers.kdt.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.kdt.customer.service.CustomerService;
import org.programmers.kdt.voucher.*;
import org.programmers.kdt.voucher.service.VoucherService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {
	private final VoucherService voucherService;
	private final CustomerService customerService;

	// 바우처 조회 API (조건별 검색 가능)
	// TODO: 현재 반환값만 JSON. 값 받는 것도 JSON 되도록 구현
	@GetMapping
	public List<VoucherDto> vouchers(@RequestParam(defaultValue = "all") String voucherType,
	                       @RequestParam(defaultValue = "all") String voucherId,
	                       @RequestParam(defaultValue = "1970-01-01") String dateFrom,
	                       @RequestParam(defaultValue = "9999-12-31") String dateTo) {
		List<Voucher> vouchers = voucherService.getVouchersWithConditions(voucherId, voucherType, dateFrom, dateTo);
		return VoucherConverter.convertToVoucherDtoList(vouchers);
	}

	// 바우처 ID로 조회 API
	@GetMapping("/{voucherId}")
	public VoucherDetailDto voucher(@PathVariable("voucherId") UUID voucherId) {
		return voucherService.getDetailInfoOf(voucherId);
	}

	// 바우처 추가 API
	@PostMapping
	public VoucherDto addVoucher(@ModelAttribute("voucherDto") VoucherDto voucherDto) {
		return VoucherConverter.convertToVoucherDto(voucherService.createdVoucher(voucherDto.getVoucherType(), voucherDto.getDiscountAmount()));
	}

	// 바우처 삭제 API
	@DeleteMapping("/{voucherId}")
	public void removeVoucher(@PathVariable("voucherId") UUID voucherId) {
		voucherService.removeVoucher(voucherId);
	}
}
