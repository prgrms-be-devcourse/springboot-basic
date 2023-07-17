package org.prgrms.kdt.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VoucherAPIController {
	private final VoucherService voucherService;

	public VoucherAPIController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/api/v1/vouchers")
	@ResponseBody
	public List<VoucherDTO> findVouchers() {
		return voucherService.getVouchers();
	}

	@GetMapping("/api/v1/vouchers/{voucherTypeIdx}")
	@ResponseBody
	public List<VoucherDTO> findVouchers(@PathVariable("voucherTypeIdx")int voucherTypeIdx, Model model) {
		VoucherType targetVoucherType = VoucherType.valueOf(voucherTypeIdx);
		return voucherService.getVouchers()
			.stream()
			.filter(voucherDTO -> voucherDTO.getVoucherType() == targetVoucherType)
			.collect(Collectors.toList());
	}
}
