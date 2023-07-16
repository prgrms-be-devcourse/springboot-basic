package co.programmers.voucher_management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
	@GetMapping("home")
	public String inquiryVoucherOf() {
		return "home";
	}
}
