package org.programmers.springbootbasic.controller.vouchers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.voucher.domain.VoucherProperty;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.programmers.springbootbasic.voucher.domain.VoucherType.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;
    private final VoucherProperty voucherProperty;
    private static final VoucherType[] VOUCHER_TYPES = values();

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VOUCHER_TYPES;
    }


    @GetMapping("voucher")
    public String createForm(Model model) {
        var voucher = new VoucherCreateForm();
        model.addAttribute("voucher", voucher);
        return "vouchers/createVoucher";
    }

    @PostMapping("voucher")
    public String createVoucher(@Valid @ModelAttribute("voucher") VoucherCreateForm form,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (isVoucherValueValid(form.getAmount(), form.getType())) {
            var createdVoucher = voucherService.createVoucher(form.getAmount(), form.getType());

            redirectAttributes.addAttribute("voucherId", createdVoucher.getId());
            return "redirect:/vouchers/{voucherId}";
        }

        return validateVoucherValue(form, bindingResult);
    }

    private boolean isVoucherValueValid(Integer amount, VoucherType type) {
        return (amount != null && type != null) && voucherService.isValidAmount(amount, type);
    }

    @GetMapping("vouchers/{voucherId}")
    public String getVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        VoucherDto voucher = VoucherDto.from(voucherService.getVoucher(voucherId));
        model.addAttribute("voucher", voucher);

        return "vouchers/editVoucher";
    }

    //TODO: 바우처 할당하기
    @PostMapping("vouchers/{voucherId}")
    public String modifyVoucher(@PathVariable("voucherId") UUID voucherId, @Valid @ModelAttribute("voucher") VoucherUpdateForm form,
                                BindingResult bindingResult) {
        log.info("postRequest for VoucherId = {}", voucherId);
        if (isVoucherValueValid(form.getAmount(), form.getType())) {
//            voucherService.updateVoucher();
            return "redirect:vouchers/{voucherId}";
        }
        return "redirect:/vouchers";
    }

    @PostMapping("vouchers/{voucherId}/delete")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        log.info("deleteRequest for voucherId = {}", voucherId);
        voucherService.deleteVoucher(voucherId);
        return "redirect:/vouchers";
    }

    private String validateVoucherValue(VoucherCreateForm form, BindingResult bindingResult) {
        int minimumAmount = 0;
        int maximumAmount = 0;
        if (form.getType()== FIXED) {
            minimumAmount=voucherProperty.getFixed().minimumAmount();
            maximumAmount=voucherProperty.getFixed().maximumAmount();
        }
        if (form.getType()== RATE) {
            minimumAmount=voucherProperty.getRate().minimumAmount();
            maximumAmount=voucherProperty.getRate().maximumAmount();
        }

        bindingResult.reject("form.voucher.value", new Object[]{form.getType().getName(), minimumAmount, maximumAmount}, "부적합한 값입니다.");
        return "vouchers/createVoucher";
    }

    @GetMapping("vouchers")
    public String voucherList(Model model) {
        List<VoucherDto> vouchers = new ArrayList<>();
        voucherService.getAllVouchers().forEach(voucher -> vouchers.add(VoucherDto.from(voucher)));
        model.addAttribute("vouchers", vouchers);

        return "vouchers/voucherList";
    }
}