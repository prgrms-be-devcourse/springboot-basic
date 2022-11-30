package org.prgrms.kdt.controller;

import org.prgrms.kdt.data.VoucherDTO;
import org.prgrms.kdt.exceptions.AmountException;
import org.prgrms.kdt.voucher.Voucher;
import org.prgrms.kdt.voucher.VoucherWebProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

import static org.prgrms.kdt.utils.VoucherType.getVoucherTypes;

@Controller
@Profile("jdbc")
@RequestMapping("/voucher/vouchers")
public class VoucherWebManager {

    private static final Logger logger = LoggerFactory.getLogger(VoucherWebManager.class);

    private final VoucherWebProvider voucherWebProvider;

    @Autowired
    public VoucherWebManager(VoucherWebProvider voucherWebProvider) {
        this.voucherWebProvider = voucherWebProvider;
    }

    @GetMapping
    public String vouchers(Model model) {
        List<Voucher> vouchers = voucherWebProvider.getVoucherList();

        model.addAttribute("vouchers", vouchers);

        return "voucher/vouchers";
    }

    @GetMapping("/{voucherId}")
    public String getVoucherDetail(@PathVariable String voucherId, Model model) {

        Voucher voucher = voucherWebProvider.findVoucher(voucherId);

        model.addAttribute("voucher", voucher);
        return "voucher/voucher";

    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("voucherTypes", getVoucherTypes());
        model.addAttribute("voucherDTO", new VoucherDTO());
        return "voucher/addForm";
    }

    @PostMapping("/add")
    public String save(@ModelAttribute("voucherDTO") VoucherDTO voucherDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        if (hasNoVoucherType(voucherDTO.getVoucherType())) {
            bindingResult.addError(new FieldError("voucherDTO", "voucherType", "바우처 타입을 반드시 선택해야 합니다."));
        }

        if (hasNoAmount(voucherDTO.getAmount())) {
            bindingResult.addError(new FieldError("voucherDTO", "amount", "할인 정도는 숫자여야 하며, 반드시 입력해야 합니다."));
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("voucherTypes", getVoucherTypes());
            return "voucher/addForm";
        }


        String voucherType = voucherDTO.getVoucherType();
        Integer amount = voucherDTO.getAmount();

        try {
            Voucher newVoucher = voucherWebProvider.createVoucher(voucherType, amount);

            redirectAttributes.addAttribute("voucherId", newVoucher.getVoucherId());
        } catch (AmountException amountException) {
            logger.info(amountException.getMessage());
            bindingResult.addError(new FieldError("voucherDTO", "amount", amountException.getMessage()));
            model.addAttribute("voucherTypes", getVoucherTypes());
            return "voucher/addForm";
        }

        return "redirect:/voucher/vouchers/{voucherId}";
    }

    @GetMapping("/{voucherId}/delete")
    public String delete(@PathVariable String voucherId) {
        voucherWebProvider.deleteVoucher(voucherId);

        return "voucher/delete";
    }


    private boolean hasNoVoucherType(String input) {
        return input == null;
    }

    private boolean hasNoAmount(Integer input) {
        return input == null;
    }

}
