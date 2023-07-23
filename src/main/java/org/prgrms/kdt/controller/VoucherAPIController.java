package org.prgrms.kdt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VoucherAPIController {
	private final VoucherService voucherService;

	public VoucherAPIController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/api/vouchers")
	@ResponseBody
	public List<VoucherDTO> findVouchers() {
		return voucherService.getVouchers();
	}

	@GetMapping("/api/vouchers/voucher-type/{voucherTypeIdx}")
	@ResponseBody
	public List<VoucherDTO> findVouchers(@PathVariable("voucherTypeIdx")int voucherTypeIdx, Model model) {
		VoucherType targetVoucherType = VoucherType.valueOf(voucherTypeIdx);
		return voucherService.getVouchers()
			.stream()
			.filter(voucherDTO -> voucherDTO.getVoucherType() == targetVoucherType)
			.collect(Collectors.toList());
	}

	@GetMapping("/api/vouchers/{voucherId}")
	@ResponseBody
	public VoucherDTO findVoucherById(@PathVariable("voucherId")Long voucherId, Model model) {
		return voucherService.findVoucherById(voucherId).orElse(null);
	}

	@DeleteMapping("/api/vouchers/{voucherId}")
	@ResponseBody
	public ResponseEntity<?> deleteVoucherById(@PathVariable("voucherId")Long voucherId) {
		voucherService.deleteVoucherById(voucherId);
		return ResponseEntity.ok("Voucher deleted");
	}

	@PostMapping("/api/vouchers")
	@ResponseBody
	public ResponseEntity<?> createVoucher(@RequestBody VoucherDTO voucherDTO) {
		voucherService.createVoucher(voucherDTO);
		return ResponseEntity.ok("Voucher created");
	}
}
