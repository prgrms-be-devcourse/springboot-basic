package com.voucher.vouchermanagement.web.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.voucher.vouchermanagement.domain.voucher.dto.UpdateVoucherRequest;
import com.voucher.vouchermanagement.domain.voucher.dto.CreateVoucherRequest;
import com.voucher.vouchermanagement.domain.voucher.dto.VoucherDto;
import com.voucher.vouchermanagement.domain.voucher.service.VoucherService;

@Controller
public class VoucherController {

	private final VoucherService voucherService;

	public VoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/vouchers")
	public String vouchersPage(Model model) {
		List<VoucherDto> vouchers = this.voucherService.findAll();
		model.addAttribute("vouchers", vouchers);

		return "vouchers";
	}

	@GetMapping("/vouchers/{id}")
	public String voucherDetailPage(@PathVariable("id") UUID id, Model model) {
		VoucherDto foundVoucher = this.voucherService.findById(id);
		model.addAttribute("voucher", foundVoucher);

		return "voucher-detail";
	}

	@PostMapping("/vouchers")
	public String create(CreateVoucherRequest request) {
		this.voucherService.create(request.getType(), request.getValue());

		return "redirect:/vouchers";
	}

	@DeleteMapping("/vouchers/{id}")
	public String deleteById(@PathVariable("id") UUID id) {
		this.voucherService.deleteById(id);

		return "redirect:/vouchers";
	}

	@GetMapping("/new-voucher")
	public String newVoucherPage() {
		return "new-voucher";
	}

	@PostMapping("/vouchers/update")
	public String update(UpdateVoucherRequest request) {
		this.voucherService.update(request);

		return "redirect:/vouchers";
	}

}
