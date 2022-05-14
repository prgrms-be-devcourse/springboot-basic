package org.programmers.kdt.weekly.voucher.controller;

import java.util.UUID;

import org.programmers.kdt.weekly.voucher.controller.restController.CreateVoucherRequest;
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

	public VoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/new-voucher")
	public String createVoucherPage(Model model) {
		model.addAttribute("voucherType", VoucherType.values());

		return "new-voucher";
	}

	@PostMapping("/new-voucher")
	public String create(CreateVoucherRequest createVoucherRequest) {
		this.voucherService.save(createVoucherRequest.voucherType(), createVoucherRequest.value());

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
		model.addAttribute("voucher", voucher.get());

		return "voucher-detail";
	}

	@DeleteMapping("/voucher/{voucherId}")
	public String delete(@PathVariable("voucherId") UUID voucherId) {
		this.voucherService.deleteById(voucherId);

		return "redirect:/voucher";
	}

	@PostMapping("/voucher/{voucherId}")
	public String update(@PathVariable("voucherId") UUID voucherId,
		CreateVoucherRequest createVoucherRequest) {
		var voucher = this.voucherService.findById(voucherId).get();
		voucher.changeValue(createVoucherRequest.value());
		this.voucherService.update(voucher);

		return "redirect:/voucher";
	}
}
