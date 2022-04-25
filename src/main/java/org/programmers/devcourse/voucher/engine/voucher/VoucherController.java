package org.programmers.devcourse.voucher.engine.voucher;


import java.time.LocalDateTime;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.customer.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VoucherController {

  private final VoucherService voucherService;

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping("/api/voucher")
  @ResponseBody()
  public ResponseEntity<Customer> getHello() {
    return ResponseEntity.ok(
        new Customer(UUID.randomUUID(), "kim", "epicblue@hanmail.net", null, LocalDateTime.now()));
  }

  @GetMapping("/voucher")
  public ModelAndView getAllVouchers(ModelAndView modelAndView) {
    var vouchers = voucherService.getAllVouchers();
    modelAndView.addObject("vouchers", vouchers);
    modelAndView.setViewName("vouchers.html");
    return modelAndView;
  }
}
