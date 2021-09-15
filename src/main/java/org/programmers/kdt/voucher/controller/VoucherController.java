package org.programmers.kdt.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.kdt.customer.Customer;
import org.programmers.kdt.customer.service.CustomerService;
import org.programmers.kdt.voucher.Voucher;
import org.programmers.kdt.voucher.VoucherDto;
import org.programmers.kdt.voucher.VoucherType;
import org.programmers.kdt.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Controller
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class VoucherController {
	private final VoucherService voucherService;
	private final CustomerService customerService;

	// 바우처 조회하기 (조건별 검색 가능)
	@GetMapping
	public String vouchers(@RequestParam(defaultValue = "all") String voucherType,
	                       @RequestParam(defaultValue = "all") String voucherId,
						   @RequestParam(defaultValue = "1970-01-01") String dateFrom,
						   @RequestParam(defaultValue = "9999-12-31") String dateTo,
	                       Model model) {
		// 날짜 범위 검색 조건.
		Timestamp from = Timestamp.valueOf(dateFrom + " 00:00:00");
		Timestamp to = Timestamp.valueOf(dateTo + " 23:59:59");
		List<Voucher> vouchers = voucherService.getVouchersBetween(from, to);

		// Voucher ID 검색 조건이 지정되었을 경우
		if (!voucherId.equals("all")) {
			Optional<Voucher> foundVoucher = vouchers.stream().filter(voucher -> voucher.getVoucherId().equals(UUID.fromString(voucherId))).findAny();
			if (foundVoucher.isPresent()) {
				vouchers = List.of(foundVoucher.get());
			} else {
				vouchers.clear();
			}
		}

		// Voucher Type 검색 조건이 지정되었을 경우
		if (!voucherType.equals("all")) {
			VoucherType type = VoucherType.of(voucherType);
			vouchers = vouchers.stream().filter(voucher -> voucher.getVoucherType().equals(type)).toList();
		}

		model.addAttribute("vouchers", vouchers);
		return "/vouchers";
	}

	// 특정 바우처 상세보기 페이지
	@GetMapping("/{voucherId}")
	public String voucher(@PathVariable("voucherId") UUID voucherId, Model model) {
		Voucher voucher = voucherService.getVoucher(voucherId).get();
		model.addAttribute("voucher", voucher);

		Optional<UUID> ownerId = voucherService.findCustomerIdHoldingVoucherOf(voucher);
		if (ownerId.isPresent()) {
			Customer owner = customerService.findCustomerById(ownerId.get()).get();
			model.addAttribute("ownerId", "%s ( %s )".formatted(owner.getCustomerId(), owner.getName()));
		} else {
			model.addAttribute("ownerId", "----");
		}
		return "/voucher";
	}

	// 바우처 추가 페이지 접속
	@GetMapping("/newVoucher")
	public String newVoucherForm() {
		return "/newVoucherForm";
	}

	// 바우처 추가 등록 실행
	@PostMapping("/newVoucher")
	public String addVoucher(@ModelAttribute("voucherDto") VoucherDto voucherDto, RedirectAttributes redirectAttributes) {
		Voucher voucher = voucherService.createdVoucher(voucherDto.getVoucherType(), voucherDto.getDiscountAmount());
		redirectAttributes.addAttribute("voucherId", voucher.getVoucherId());
		return "redirect:/vouchers/{voucherId}";
	}

	// 바우처 삭제 기능
	@GetMapping("/{voucherId}/remove")
	public String removeVoucher(@PathVariable("voucherId") UUID voucherId) {
		voucherService.removeVoucher(voucherId);
		return "redirect:/vouchers";
	}
}
