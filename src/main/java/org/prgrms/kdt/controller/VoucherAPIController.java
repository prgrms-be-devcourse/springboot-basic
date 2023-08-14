package org.prgrms.kdt.controller;

import java.util.List;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.dto.VoucherRequest;
import org.prgrms.kdt.model.dto.VoucherResponse;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoucherAPIController {
	private final VoucherService voucherService;

	public VoucherAPIController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/api/vouchers")
	public List<VoucherResponse> findVouchers() {
		return voucherService.getVouchers();
	}

	@GetMapping("/api/vouchers/search")
	public List<VoucherResponse> findVouchers(
		@RequestParam(value = "voucher-type") String voucherType
	) {
		return voucherService.findVoucherByVoucherType(VoucherType.valueOf(voucherType));
	}

	@GetMapping("/api/vouchers/{voucher-id}")
	public VoucherResponse findVoucherById(@PathVariable("voucher-id")Long voucherId) {
		return voucherService.findVoucherById(voucherId);
	}

	@DeleteMapping("/api/vouchers/{voucher-id}")
	public void deleteVoucherById(@PathVariable("voucher-id")Long voucherId) {
		voucherService.deleteVoucherById(voucherId);
	}

	@PostMapping("/api/vouchers/new")
	public Long saveVoucher(@RequestBody VoucherRequest voucherRequest) {
		return voucherService.saveVoucher(voucherRequest);
	}
}
