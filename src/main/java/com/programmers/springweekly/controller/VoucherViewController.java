package com.programmers.springweekly.controller;

import com.programmers.springweekly.dto.voucher.request.VoucherCreateRequest;
import com.programmers.springweekly.dto.voucher.response.VoucherListResponse;
import com.programmers.springweekly.dto.voucher.response.VoucherResponse;
import com.programmers.springweekly.service.VoucherService;
import com.programmers.springweekly.util.validator.VoucherValidator;
import java.util.NoSuchElementException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view/voucher")
public class VoucherViewController {

    private final VoucherService voucherService;

    @GetMapping("/")
    public String getMenuPage() {
        return "voucher/menu";
    }

    @GetMapping("/save")
    public String getCreatePage() {
        return "voucher/create";
    }

    @PostMapping("/save")
    public String save(VoucherCreateRequest voucherCreateRequest) {
        VoucherValidator.validateVoucher(
                voucherCreateRequest.getVoucherType(),
                String.valueOf(voucherCreateRequest.getDiscountAmount())
        );

        voucherService.save(voucherCreateRequest);

        return "voucher/menu";
    }

    @GetMapping("/find")
    public String getFindAllPage(Model model) {
        VoucherListResponse voucherListResponse = voucherService.findAll();
        model.addAttribute("voucherList", voucherListResponse.getVoucherList());

        return "voucher/findAll";
    }

    @GetMapping("/find/{id}")
    public String findById(@PathVariable("id") UUID voucherId, Model model) {
        VoucherResponse voucherResponse = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucherResponse);

        return "voucher/find";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") UUID voucherId) {
        boolean isExistVoucherId = voucherService.existById(voucherId);

        if (!isExistVoucherId) {
            throw new NoSuchElementException("사용자가 삭제하려는 바우처 " + voucherId + "는 없는 ID입니다.");
        }

        voucherService.deleteById(voucherId);

        return "redirect:/view/voucher/find";
    }

}
