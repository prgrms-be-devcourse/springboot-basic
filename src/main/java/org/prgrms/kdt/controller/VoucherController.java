package org.prgrms.kdt.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.dto.VoucherRequest;
import org.prgrms.kdt.model.dto.VoucherResponse;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.servlet.KdtWebApplicationInitializer;
import org.prgrms.kdt.util.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class VoucherController {
	private final VoucherService voucherService;

	private static final Logger logger = LoggerFactory.getLogger(KdtWebApplicationInitializer.class);

	public VoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/vouchers")
	public String viewVouchersPage(Model model) {
		List<VoucherResponse> allCustomers = voucherService
			.getVouchers()
			.stream().map(voucherDTO -> VoucherFactory.getVoucherResponse(voucherDTO))
			.collect(Collectors.toList());

		model.addAttribute("serverTime", LocalDateTime.now());
		model.addAttribute("vouchers", allCustomers);
		return "vouchers";
	}

	@GetMapping("/vouchers/new")
	public String viewNewCustomerPage() {
		return "new-vouchers";
	}

	@PostMapping("/vouchers/new")
	public String addNewVoucher(VoucherRequest voucherRequest) {
		int amount = voucherRequest.getAmount();
		int voucherTypeIdx = voucherRequest.getVoucherTypeIdx();

		VoucherDTO newVoucherDTO = VoucherFactory.getVoucherDTO(amount, VoucherType.valueOf(voucherTypeIdx));
		voucherService.createVoucher(newVoucherDTO);
		return "redirect:/vouchers";
	}
}
