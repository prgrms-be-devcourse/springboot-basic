package org.prgrms.springorder.controller.voucher;

import java.util.List;
import java.util.UUID;

import org.prgrms.springorder.controller.dto.VoucherRequestDto;
import org.prgrms.springorder.controller.dto.VoucherResponseDto;
import org.prgrms.springorder.service.voucher.VoucherService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoucherWebController {

	private final VoucherService voucherService;

	public VoucherWebController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/")
	public String getList(Model model) {
		List<VoucherResponseDto> voucherList = voucherService.getList();
		model.addAttribute("voucherList",voucherList);
		return "voucher/voucherList";
	}

	@GetMapping("/voucher/{voucherId}")
	public String get(@PathVariable(name = "voucherId") UUID id, Model model) {
		VoucherResponseDto voucherResponseDto = voucherService.findById(id);
		model.addAttribute("voucher", voucherResponseDto);
		return "voucher/voucherInfo";
	}

	@GetMapping("/voucher")
	public String create() {
		return "voucher/voucherForm";
	}

	@PostMapping("/voucher")
	public String post(VoucherRequestDto voucherRequestDto) {
		voucherService.createVoucher(voucherRequestDto.getVoucherType(),voucherRequestDto.getValue());
		return "redirect:/";
	}

	@DeleteMapping("/voucher/{voucherId}")
	public String delete(@PathVariable(name = "voucherId") UUID uuid) {
		voucherService.deleteById(uuid);
		return "redirect:/";
	}
}
