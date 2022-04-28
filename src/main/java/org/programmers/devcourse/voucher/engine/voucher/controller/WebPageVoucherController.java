package org.programmers.devcourse.voucher.engine.voucher.controller;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.VoucherService;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller()
@RequestMapping("/voucher")
public class WebPageVoucherController {

  private final VoucherService voucherService;

  public WebPageVoucherController(VoucherService voucherService) {
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
  public String registerVoucher(@ModelAttribute @Valid VoucherRegistrationDto voucherRegistrationDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new VoucherException(bindingResult.toString());
    }
    voucherService.create(voucherRegistrationDto.getVoucherType(), voucherRegistrationDto.getDiscountDegree());
    return "redirect:/voucher";
  }

  @PostMapping("/delete")
  public String deleteVoucher(@RequestParam @Valid @NotNull UUID voucherId) {
    voucherService.remove(voucherId);
    return "redirect:/voucher";
  }
}
