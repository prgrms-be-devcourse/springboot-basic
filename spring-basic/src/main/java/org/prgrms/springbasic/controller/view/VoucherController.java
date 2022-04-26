package org.prgrms.springbasic.controller.view;

import lombok.RequiredArgsConstructor;
import org.prgrms.springbasic.controller.view.dto.VoucherDto;
import org.prgrms.springbasic.domain.voucher.Voucher;
import org.prgrms.springbasic.service.web.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.prgrms.springbasic.controller.view.dto.VoucherDto.assignVoucherDtoFrom;
import static org.prgrms.springbasic.controller.view.dto.VoucherDto.updateVoucherDtoFrom;

@Controller
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    
    private final VoucherService voucherService;

    //조회
    @GetMapping
    public String voucherList(Model model) {
        var vouchers = voucherService.findVouchers();

        model.addAttribute("vouchers", vouchers);

        return "/voucher/vouchers";
    }

    @GetMapping("/{customerId}/voucher")
    public String customerVoucherList(@PathVariable UUID customerId, Model model) {
        var vouchers = voucherService.findVouchersByCustomerId(customerId);

        model.addAttribute("vouchers", vouchers);

        return "/voucher/customerVouchers";
    }

    //상세
    @GetMapping("/{voucherId}/detail")
    public String voucherDetails(@PathVariable UUID voucherId, Model model) {
        Voucher retrievedVoucher = voucherService.findVoucherByVoucherId(voucherId);

        model.addAttribute("voucher", retrievedVoucher);

        return "/voucher/voucherDetail";
    }

    //입력
    @GetMapping("/new")
    public String voucherAdd(Model model) {
        model.addAttribute("newVoucherDto", new VoucherDto());

        return "/voucher/voucherAddForm";
    }

    @PostMapping("/new")
    public String voucherAdd(@ModelAttribute VoucherDto voucherDto) {
        var voucherType = voucherDto.getVoucherType();

        var voucher = voucherType.createVoucher(voucherDto.getDiscountInfo());

        voucherService.addVoucher(voucher);

        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}/renewal")
    public String voucherModify(@PathVariable UUID voucherId, Model model) {
        Voucher retrievedVoucher = voucherService.findVoucherByVoucherId(voucherId);

        model.addAttribute("updateVoucherDto", updateVoucherDtoFrom(retrievedVoucher));

        return "/voucher/voucherModifyForm";
    }

    @PostMapping("/{voucherId}/renewal")
    public String voucherModify(@PathVariable UUID voucherId, @ModelAttribute VoucherDto updateVoucherDto) {
        Voucher retrievedVoucher = voucherService.findVoucherByVoucherId(voucherId);

        voucherService.modifyVoucher(retrievedVoucher, updateVoucherDto);

        return "redirect:/vouchers";
    }

    @GetMapping("/{voucherId}/assignment")
    public String voucherAssign(@PathVariable UUID voucherId, Model model) {
        Voucher retrievedVoucher = voucherService.findVoucherByVoucherId(voucherId);

        model.addAttribute("assignVoucherDto", assignVoucherDtoFrom(retrievedVoucher));

        return "/voucher/voucherAssignForm";
    }

    @PostMapping("/{voucherId}/assignment")
    public String voucherAssign(@PathVariable UUID voucherId, @ModelAttribute VoucherDto assignVoucherDto) {
        Voucher retrievedVoucher = voucherService.findVoucherByVoucherId(voucherId);

        voucherService.assignToCustomer(retrievedVoucher, assignVoucherDto.getCustomerId());

        return "redirect:/vouchers";
    }

    //삭제
    @PostMapping("/{voucherId}/removal")
    public String voucherRemove(@PathVariable UUID voucherId) {
        voucherService.removeVoucherById(voucherId);

        return "redirect:/vouchers";
    }

    @PostMapping("/removal")
    public String voucherRemoveAll() {
        voucherService.removeVouchers();

        return "redirect:/vouchers";
    }
}
