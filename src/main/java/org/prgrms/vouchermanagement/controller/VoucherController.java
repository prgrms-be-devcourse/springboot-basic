package org.prgrms.vouchermanagement.controller;

import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VoucherController {

  private final VoucherService voucherService;

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping("/")
  public String testPage() {
    return "voucher-list";
  }

  @GetMapping("/vouchers")
  public String vouchersPage() {
    List<Voucher> vouchers = voucherService.getVoucherList();
//    model.addAttribute("vouchers",);
    return "voucher-list";
  }
}
