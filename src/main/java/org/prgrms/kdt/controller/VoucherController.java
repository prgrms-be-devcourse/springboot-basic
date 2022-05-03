package org.prgrms.kdt.controller;

import java.util.UUID;
import org.prgrms.kdt.dto.VoucherDto;
import org.prgrms.kdt.dto.VoucherRequest;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

  private final VoucherService voucherService;

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping
  public String getVouchers(Model model) {
    model.addAttribute("vouchers", voucherService.findAll());
    return "/voucher/vouchers";
  }

  @GetMapping("/{voucherId}")
  public String getVoucher(Model model, @PathVariable UUID voucherId) {
    var voucher = voucherService.findById(voucherId);
    model.addAttribute("voucher", voucher);
    return "/voucher/voucher";
  }

  @GetMapping("/new")
  public String registerVoucher() {
    return "/voucher/register-voucher";
  }

  @PostMapping
  public String createVoucher(VoucherRequest.CreateRequest createRequest) {
    var voucherDto = VoucherDto.of(createRequest);
    voucherService.create(voucherDto);
    return "redirect:/vouchers";
  }

  @DeleteMapping("/{voucherId}")
  public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
    voucherService.deleteById(voucherId);
    return "redirect:/vouchers";
  }
}