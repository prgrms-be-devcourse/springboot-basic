package study.dev.spring.voucher.presentation;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import study.dev.spring.voucher.application.VoucherService;
import study.dev.spring.voucher.application.dto.CreateVoucherRequest;
import study.dev.spring.voucher.application.dto.VoucherInfo;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vouchers")
public class WebVoucherController {

	private final VoucherService voucherService;

	@GetMapping("/new")
	public String renderNewVoucherPage() {
		return "/vouchers/new_voucher";
	}

	@PostMapping
	public String createVoucher(@ModelAttribute CreateVoucherRequest request) {
		voucherService.createVoucher(request);

		return "redirect:/vouchers";
	}

	@GetMapping
	public String getAllVouchers(Model model) {
		List<VoucherInfo> result = voucherService.getAllVouchers();
		model.addAttribute("voucherInfoList", result);

		return "/vouchers/voucher_info";
	}

	@GetMapping("/customer")
	public String getVouchersByCustomer(
		@RequestParam String customerId,
		Model model
	) {
		List<VoucherInfo> result = voucherService.getVouchersByCustomer(customerId);
		model.addAttribute("voucherInfoList", result);

		return "/vouchers/voucher_info";
	}
}
