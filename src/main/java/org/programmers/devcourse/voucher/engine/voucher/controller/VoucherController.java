package org.programmers.devcourse.voucher.engine.voucher.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/voucher")
public class VoucherController {

  private final VoucherService voucherService;

  public VoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping("")
  public ModelAndView showAllVouchers(ModelAndView modelAndView) {
    var vouchers = voucherService
        .getAllVouchers()
        .stream()
        .map(VoucherDto::from)
        .collect(Collectors.toList());

    modelAndView.addObject("vouchers", vouchers);
    modelAndView.addObject("serverTime", DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now()));
    modelAndView.setViewName("vouchers.html");
    return modelAndView;
  }

  @GetMapping("/{voucherId}")
  public ModelAndView showVoucherById(ModelAndView modelAndView, @PathVariable UUID voucherId) {
    Voucher voucher = voucherService.getVoucherById(voucherId);

    modelAndView.addObject("voucher", VoucherDto.from(voucher));
    modelAndView.setViewName("voucher.html");
    return modelAndView;
  }

  @GetMapping("/register")
  public String showVoucherRegistrationForm(ModelAndView modelAndView) {
    return "new-vouchers.html";
  }

  @PostMapping("/register")
  public String registerVoucher(@ModelAttribute VoucherRegistrationDto voucherRegistrationDto) {
    voucherService.create(voucherRegistrationDto.getVoucherType(), voucherRegistrationDto.getDiscountDegree());
    return "redirect:/voucher";
  }

  @PostMapping("/delete")
  public String deleteVoucher(@RequestParam UUID voucherId) {
    voucherService.remove(voucherId);
    return "redirect:/voucher";
  }
}
