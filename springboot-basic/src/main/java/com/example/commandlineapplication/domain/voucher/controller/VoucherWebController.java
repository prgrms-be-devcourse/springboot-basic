package com.example.commandlineapplication.domain.voucher.controller;

import com.example.commandlineapplication.domain.voucher.dto.request.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.dto.response.VoucherResponse;
import com.example.commandlineapplication.domain.voucher.service.VoucherService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class VoucherWebController {

  private final VoucherService voucherService;

  @GetMapping
  public String home() {
    return "home";
  }

  @GetMapping("/vouchers")
  public String getVouchers(Model model) {
    List<VoucherResponse> vouchers = voucherService.findVouchers();
    model.addAttribute("vouchers", vouchers);
    return "vouchers";
  }

  @GetMapping("/voucher/{voucherId}")
  public String voucherDetailPage(Model model, @PathVariable String voucherId) {
    VoucherResponse voucher = voucherService.getVoucher(voucherId);
    model.addAttribute("voucher", voucher);
    return "voucher-detail";
  }

  @GetMapping("/voucher/new")
  public String newVoucherPage() {
    return "voucher-new";
  }

  @PostMapping("/voucher/new")
  public String createVoucher(VoucherCreateRequest voucherCreateRequest) {
    voucherService.createVoucher(voucherCreateRequest.getVoucherType(),
        voucherCreateRequest.getDiscountAmount());
    return "redirect:/vouchers";
  }

  @PostMapping("/voucher/delete/{voucherId}")
  public String deleteVoucher(@PathVariable String voucherId) {
    voucherService.deleteVoucher(UUID.fromString(voucherId));
    return "redirect:/vouchers";
  }
}
