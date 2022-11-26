package com.programmers.voucher.web.voucher;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.service.VoucherWebService;
import com.programmers.voucher.web.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.web.voucher.dto.VoucherResponseDto;

@Controller
@RequestMapping("/voucher")
public class VoucherController {

	private final VoucherWebService voucherWebService;

	public VoucherController(VoucherWebService voucherWebService) {
		this.voucherWebService = voucherWebService;
	}

	@GetMapping
	public String voucherHome() {
		return "voucher/voucherHome";
	}

	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("voucher", new VoucherRequestDto());
		model.addAttribute("voucherTypes", VoucherType.values());
		return "voucher/addForm";
	}

	@PostMapping("/add")
	public String addVoucher(@ModelAttribute("voucher") VoucherRequestDto voucherRequestDto,
		RedirectAttributes redirectAttributes) {
		VoucherResponseDto voucherResponseDto = voucherWebService.createVoucher(voucherRequestDto);
		redirectAttributes.addAttribute("voucherId", voucherResponseDto.getVoucherId());
		return "redirect:/voucher/{voucherId}";
	}

	@GetMapping("/{voucherId}")
	public String voucher(@PathVariable UUID voucherId, Model model) {
		VoucherResponseDto voucherResponseDto = voucherWebService.findById(voucherId);
		model.addAttribute("voucher", voucherResponseDto);
		return "voucher/voucher";
	}

	@GetMapping("/all")
	public String allVouchers(Model model) {
		List<VoucherResponseDto> voucherResponseDtoList = voucherWebService.getAllVoucher();
		model.addAttribute("vouchers", voucherResponseDtoList);
		return "voucher/vouchers";
	}

	@GetMapping("/delete")
	public String removeForm(Model model) {
		List<VoucherResponseDto> voucherResponseDtoList = voucherWebService.getAllVoucher();
		model.addAttribute("vouchers", voucherResponseDtoList);
		return "voucher/deleteForm";
	}

	@DeleteMapping("/delete/{voucherId}")
	public String removeVoucher(@PathVariable UUID voucherId) {
		voucherWebService.removeVoucher(voucherId);
		return "redirect:/voucher/delete";
	}
}
