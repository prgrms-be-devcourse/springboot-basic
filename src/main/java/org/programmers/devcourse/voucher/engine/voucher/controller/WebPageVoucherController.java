package org.programmers.devcourse.voucher.engine.voucher.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class WebPageVoucherController {

  private final VoucherService voucherService;

  public WebPageVoucherController(VoucherService voucherService) {
    this.voucherService = voucherService;
  }

  @GetMapping("/voucher")
  public ModelAndView showAllVouchers(ModelAndView modelAndView) {
    var vouchers = voucherService
        .getAllVouchers()
        .stream()
        .map(VoucherDto::from)
        .collect(Collectors.toList());
    modelAndView.addObject("vouchers", vouchers);
    modelAndView.addObject("serverTime",
        DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(LocalDateTime.now()));
    modelAndView.setViewName("vouchers.html");
    return modelAndView;
  }

  @GetMapping("/voucher/{voucherId}")
  public ModelAndView showVoucherById(ModelAndView modelAndView, @PathVariable UUID voucherId) {
    Voucher voucher = voucherService.getVoucherById(voucherId);
    modelAndView.addObject("voucher", VoucherDto.from(voucher));
    modelAndView.setViewName("voucher.html");
    return modelAndView;
  }

  @GetMapping("/voucher/register")
  public String showVoucherRegistrationForm() {
    return "new-vouchers.html";
  }

  @PostMapping("/voucher/register")
  public String registerVoucher(
      @ModelAttribute @Valid VoucherRegistrationDto voucherRegistrationDto,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new VoucherException(bindingResult.toString());
    }
    voucherService.create(voucherRegistrationDto.getVoucherType(),
        voucherRegistrationDto.getDiscountDegree());
    return "redirect:/voucher";
  }

  @PostMapping("/voucher/delete")
  public String deleteVoucher(@RequestParam UUID voucherId) {
    voucherService.remove(voucherId);
    return "redirect:/voucher";
  }
}
