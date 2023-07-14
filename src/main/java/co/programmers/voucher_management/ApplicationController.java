package co.programmers.voucher_management;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.service.VoucherService;

@Controller
public class ApplicationController {
	@GetMapping("home")
	public String inquiryVoucherOf(){
		return "home";
	}
}
