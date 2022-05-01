package org.prgrms.vouchermanagement.controller;

import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.dto.UpdatedVoucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

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
    model.addAttribute("vouchers", voucherService.getVoucherList());
    return "voucher-list";
  }

  @GetMapping("/voucher")
  public String voucherPage(@RequestParam("id") UUID voucherId, Model model) {
    Optional<Voucher> found = voucherService.getVoucherById(voucherId);
    if(found.isPresent()) {
      model.addAttribute("voucher", found.get());
      return "voucher";
    }
    return "redirect:/voucher-list";
  }

  @PostMapping("/update-voucher")
  public String updateVoucher(UpdatedVoucher updatedVoucher) {
    voucherService.updateVoucher(updatedVoucher);
    return "redirect:/vouchers";
  }
}
