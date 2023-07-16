package org.prgrms.kdt.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.prgrms.kdt.model.dto.VoucherDTO;
import org.prgrms.kdt.model.entity.VoucherEntity;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.servlet.KdtWebApplicationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class VoucherController {
	private final VoucherService voucherService;

	private static final Logger logger = LoggerFactory.getLogger(KdtWebApplicationInitializer.class);

	public VoucherController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping("/vouchers")
	public String viewVouchersPage(Model model) {
		logger.info("visit viewVouchersPage");
		List<VoucherDTO> allCustomers = voucherService.getVouchers();
		model.addAttribute("serverTime", LocalDateTime.now());
		model.addAttribute("vouchers", allCustomers);
		return "vouchers";
	}

}
