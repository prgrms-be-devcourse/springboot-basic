package org.programmers.kdt.weekly.voucher.controller;

import java.util.UUID;

import org.programmers.kdt.weekly.voucher.controller.restController.VoucherDto.VoucherCreateRequest;
import org.programmers.kdt.weekly.voucher.controller.restController.VoucherDto.VoucherUpdateRequest;
import org.programmers.kdt.weekly.voucher.converter.VoucherConverter;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.programmers.kdt.weekly.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoucherController {

	private final VoucherService voucherService;
	private final VoucherConverter voucherConverter;

	public VoucherController(VoucherService voucherService,
		VoucherConverter voucherConverter) {
		this.voucherService = voucherService;
		this.voucherConverter = voucherConverter;
	}

	@GetMapping("/new-voucher")
	public String createVoucherPage(Model model) {
		model.addAttribute("voucherType", VoucherType.values());

		return "new-voucher";
	}

	@PostMapping("/new-voucher")
	public String create(VoucherCreateRequest voucherCreateRequest) {
		this.voucherService.save(voucherCreateRequest.voucherType(), voucherCreateRequest.value());

		return "redirect:/";
	}

	@GetMapping("/vouchers")
	public String list(Model model) {
		var vouchers = this.voucherService.getVouchers();
		model.addAttribute("vouchers", vouchers);

		return "voucher-list";
	}

	@GetMapping("/voucher/{voucherId}")
	public String detail(@PathVariable("voucherId") UUID voucherId, Model model) {
		var voucher = this.voucherService.findById(voucherId);
		model.addAttribute("voucher", voucher);

		return "voucher-detail";
	}

	@DeleteMapping("/voucher/{voucherId}")
	public String delete(@PathVariable("voucherId") UUID voucherId) {
		this.voucherService.deleteById(voucherId);

		return "redirect:/voucher";
	}

	@PostMapping("/voucher/{voucherId}")
	public String update(@PathVariable("voucherId") UUID voucherId,
		VoucherUpdateRequest voucherUpdateRequest) {
		var voucherResponseDto = this.voucherService.findById(voucherId);
		var voucher = voucherConverter.convertVoucher(voucherResponseDto);
		voucher.changeValue(voucherUpdateRequest.value());
		this.voucherService.update(voucher);

		return "redirect:/voucher";
	}
}
