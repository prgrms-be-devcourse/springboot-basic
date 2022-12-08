package org.prgrms.springbootbasic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prgrms.springbootbasic.dto.VoucherInputDto;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.exception.VoucherNotFoundException;
import org.prgrms.springbootbasic.service.VoucherService;
import org.prgrms.springbootbasic.util.UUIDUtil;
import org.prgrms.springbootbasic.validator.VoucherInputDtoViewValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final VoucherService voucherService;
    private final VoucherInputDtoViewValidator voucherInputDtoViewValidator;

    @InitBinder("voucherInputDto")
    public void init(WebDataBinder dataBinder) {
        log.info("init binder {}", dataBinder);
        dataBinder.addValidators(voucherInputDtoViewValidator);
    }

    @GetMapping(value = "/home")
    public String home() {
        return "vouchers/home";
    }

    @GetMapping(value = "/new")
    public String createVoucher(Model model) {
        model.addAttribute("voucherInputDto", new VoucherInputDto());
        return "vouchers/createPage";
    }

    @PostMapping(value = "/new")
    public String createVoucher(@Validated @ModelAttribute("voucherInputDto") VoucherInputDto voucherInputDto,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "vouchers/createPage";
        }

        Voucher voucher = voucherService.createVoucher(voucherInputDto);
        redirectAttributes.addAttribute("voucherId", voucher.getVoucherId());
        return "redirect:/vouchers/{voucherId}";
    }

    @GetMapping(value = "/{voucherId}")
    public String lookupVoucher(@PathVariable String voucherId, Model model) {
        if (!UUIDUtil.isUUID(voucherId)) {
            throw new VoucherNotFoundException();
        }
        Voucher voucher = voucherService.lookupVoucherById(voucherId);
        model.addAttribute("voucher", voucher);
        return "vouchers/detailPage";
    }

    @GetMapping(value = "/list")
    public String lookupVoucherList(Model model) {
        List<Voucher> voucherList = voucherService.lookupVoucherList();
        model.addAttribute("voucherList", voucherList);
        return "vouchers/lookupPage";
    }
}
