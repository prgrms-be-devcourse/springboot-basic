package org.prgrms.kdt.controller;

import java.util.List;

import org.prgrms.kdt.model.dto.VoucherRequest;
import org.prgrms.kdt.model.dto.VoucherResponse;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VoucherAPIController {
	private final VoucherService voucherService;

	public VoucherAPIController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/api/vouchers")
	@ResponseBody
	public List<VoucherResponse> findVouchers() {
		return voucherService.getVouchers();
	}

	@GetMapping("/api/vouchers/search/{voucher-type}")
	@ResponseBody
	public List<VoucherResponse> findVouchers(@RequestParam("voucherType") String voucherType) {
		return voucherService.findVoucherByVoucherType(voucherType);
	}

	@GetMapping("/api/vouchers/{voucher-id}")
	@ResponseBody
	public VoucherResponse findVoucherById(@PathVariable("voucher-id")Long voucherId, Model model) {
		return voucherService.findVoucherById(voucherId);
	}

	@DeleteMapping("/api/vouchers/{voucher-id}")
	@ResponseBody
	public ResponseEntity deleteVoucherById(@PathVariable("voucher-id")Long voucherId) {
		voucherService.deleteVoucherById(voucherId);
		return ResponseEntity.ok("Voucher deleted");
	}

	@PostMapping("/api/vouchers/new")
	@ResponseBody
	public ResponseEntity saveVoucher(@RequestBody VoucherRequest voucherRequest) {
		voucherService.saveVoucher(voucherRequest);
		return ResponseEntity.ok("Voucher created");
	}
}
