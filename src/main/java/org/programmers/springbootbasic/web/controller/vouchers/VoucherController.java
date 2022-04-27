package org.programmers.springbootbasic.web.controller.vouchers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springbootbasic.voucher.domain.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.domain.VoucherProperty;
import org.programmers.springbootbasic.voucher.domain.VoucherType;
import org.programmers.springbootbasic.voucher.repository.VoucherRepository;
import org.programmers.springbootbasic.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static org.programmers.springbootbasic.voucher.domain.VoucherType.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherRepository voucherRepository;
    private final VoucherService voucherService;
    private final VoucherProperty voucherProperty;

    private static final VoucherType[] VOUCHER_TYPES = values();

    @ModelAttribute("voucherTypes")
    public VoucherType[] voucherTypes() {
        return VOUCHER_TYPES;
    }

    @PostMapping("/test-data")
    public String sampleData() {
        var voucher1 = new FixedDiscountVoucher(UUID.randomUUID(), 3000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 4, 25, 2, 40)));
        var voucher2 = new FixedDiscountVoucher(UUID.randomUUID(), 8000, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 15, 14, 15)));
        var voucher3 = new FixedDiscountVoucher(UUID.randomUUID(), 12000, null,
                Timestamp.valueOf(LocalDateTime.of(2021, 1, 22, 10, 30)));
        var voucher4 = new RateDiscountVoucher(UUID.randomUUID(), 15, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 1, 22, 10, 30)));
        var voucher5 = new RateDiscountVoucher(UUID.randomUUID(), 30, null,
                Timestamp.valueOf(LocalDateTime.of(2022, 3, 22, 10, 30)));
        voucherRepository.insert(voucher1);
        voucherRepository.insert(voucher2);
        voucherRepository.insert(voucher3);
        voucherRepository.insert(voucher4);
        voucherRepository.insert(voucher5);

        return "redirect:/";
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
        if (isFormInputValid(bindingResult.hasErrors(), form.getAmount(), form.getType())) {
            var createdVoucher = voucherService.createVoucher(form.getAmount(), form.getType());

            redirectAttributes.addAttribute("voucherId", createdVoucher.getId());
            return "redirect:/vouchers/{voucherId}";
        }

        return redirectToForm(form, bindingResult);
    }

    private boolean isFormInputValid(boolean hasError, Integer amount, VoucherType type) {
        return !hasError && (amount != null && type != null) && voucherService.isValidAmount(amount, type);
    }

    @GetMapping("vouchers/{voucherId}")
    public String getVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        VoucherDto voucher = VoucherDto.from(voucherService.getVoucher(voucherId));
        model.addAttribute("voucher", voucher);

        return "vouchers/editVoucher";
    }

    @PostMapping("vouchers/{voucherId}")
    public String modifyVoucher(@PathVariable("voucherId") UUID voucherId, @Valid @ModelAttribute("voucher") VoucherUpdateForm form,
                                BindingResult bindingResult) {
        log.info("postRequest for VoucherId = {}", voucherId);
        if (isFormInputValid(bindingResult.hasErrors(), form.getAmount(), form.getType())) {
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

    //TODO: 바우처 할당하기
//    @GetMapping("vouchers/{voucherId}/update")
//    public String updateVoucherOwnerForm(@PathVariable("voucherId") UUID voucherId, @ModelAttribute VoucherDto voucher, Model model) {
//        List<MemberDto> members = memberService.getAllMembers();
//        model.addAttribute("members", members);
//        model.addAttribute("voucher", voucher);
//
//        return "vouchers/updateVoucherOwner";
//    }


    private String redirectToForm(VoucherCreateForm form, BindingResult bindingResult) {
        if (form.getType() != null || form.getAmount() != null) {
            int minimumAmount = 0;
            int maximumAmount = 0;
            if (form.getType() == FIXED) {
                minimumAmount = voucherProperty.getFixed().minimumAmount();
                maximumAmount = voucherProperty.getFixed().maximumAmount();
            }
            if (form.getType() == RATE) {
                minimumAmount = voucherProperty.getRate().minimumAmount();
                maximumAmount = voucherProperty.getRate().maximumAmount();
            }

            bindingResult.reject("form.voucher.value", new Object[]{form.getType().getName(), minimumAmount, maximumAmount}, "부적합한 값입니다.");
        }
        return "vouchers/createVoucher";
    }

    @GetMapping("vouchers")
    public String voucherList(Model model, @RequestParam(required = false) VoucherType type,
                              @RequestParam(required = false) Date startingDate, @RequestParam(required = false) Date endingDate) {
        model.addAttribute("startingDate", startingDate);
        model.addAttribute("endingDate", endingDate);
        if (type != null) {
            if (startingDate != null && endingDate != null) {
                return voucherListByTypeAndDate(model, type, startingDate, endingDate);
            }
            return voucherListByType(model, type);
        }
        if (startingDate != null && endingDate != null) {
            return voucherListByDate(model, startingDate, endingDate);
        }

        List<VoucherDto> vouchers = new ArrayList<>();
        voucherService.getAllVouchers().forEach(voucher -> vouchers.add(VoucherDto.from(voucher)));
        model.addAttribute("vouchers", vouchers);
        return "vouchers/voucherList";
    }

    private String voucherListByTypeAndDate(Model model, VoucherType type, Date startingDate, Date endingDate) {
        model.addAttribute("type", type);
        model.addAttribute("startingDate", startingDate);
        model.addAttribute("endingDate", endingDate);
        List<VoucherDto> vouchers = voucherService.getVouchersByTypeAndDate(type, startingDate, endingDate)
                .stream().map(voucher -> VoucherDto.from(voucher)).collect(toList());
        model.addAttribute("vouchers", vouchers);
        return "vouchers/voucherList";
    }

    private String voucherListByType(Model model, VoucherType type) {
        model.addAttribute("type", type);
        List<VoucherDto> vouchers = voucherService.getVouchersByType(type)
                .stream().map(voucher -> VoucherDto.from(voucher)).collect(toList());
        model.addAttribute("vouchers", vouchers);
        return "vouchers/voucherList";
    }

    private String voucherListByDate(Model model, Date startingDate, Date endingDate) {
        model.addAttribute("startingDate", startingDate);
        model.addAttribute("endingDate", endingDate);
        List<VoucherDto> vouchers = voucherService.getVouchersByDate(startingDate, endingDate)
                .stream().map(voucher -> VoucherDto.from(voucher)).collect(toList());
        model.addAttribute("vouchers", vouchers);
        return "vouchers/voucherList";
    }
}