package org.prgrms.vouchermanagement.controller;

import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class VoucherController {

  private static final Logger log = LoggerFactory.getLogger(VoucherController.class);

  private final VoucherService voucherService;

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping("/")
  public String testPage() {
    return "voucher-list";
  }

  @GetMapping("/vouchers")
  public String vouchersPage(Model model) {
//    List<Voucher> vouchers = voucherService.getVoucherList();
//    log.info("vouchers = {}", vouchers);
    model.addAttribute("vouchers", voucherService.getVoucherList());
    return "voucher-list";
  }
}
