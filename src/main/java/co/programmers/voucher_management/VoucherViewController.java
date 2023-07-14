package co.programmers.voucher_management;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import co.programmers.voucher_management.voucher.dto.VoucherRequestDTO;
import co.programmers.voucher_management.voucher.dto.VoucherResponseDTO;
import co.programmers.voucher_management.voucher.service.VoucherService;

@RequestMapping("/vouchers")
@Controller
public class VoucherViewController {
	private final VoucherService voucherService;

	public VoucherViewController(VoucherService voucherService) {
		this.voucherService = voucherService;
	}

	@GetMapping()
	public String findAll(Model model) {
		List<VoucherResponseDTO> response = voucherService.inquiryVoucherOf();
		model.addAttribute("vouchers", response);
		return "vouchers";
	}

	@GetMapping("/{id}")
	public String findById(@PathVariable long id, Model model) {
		VoucherResponseDTO response = voucherService.findById(id);
		model.addAttribute("vouchers", response);
		return "vouchers";

	}

	@PostMapping("/{id}/delete")
	public String deleteById(@PathVariable long id, Model model) {
		voucherService.deleteById(id);
		return findAll(model);
	}

	@GetMapping("/new")
	public String create(Model model) {
		model.addAttribute("voucher", new VoucherRequestDTO());
		return "vouchers-new"; //html
		//viewResolver
	}

	@PostMapping("/new")
	public String create(@ModelAttribute("voucher") VoucherRequestDTO voucherRequestDTO) {
		voucherService.create(voucherRequestDTO);
		return "redirect:/vouchers"; //경로

	}
}
