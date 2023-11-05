package com.prgms.vouchermanager.contorller.voucher;

import com.prgms.vouchermanager.domain.voucher.Voucher;
import com.prgms.vouchermanager.dto.CreateVoucherDto;
import com.prgms.vouchermanager.dto.UpdateVoucherDto;
import com.prgms.vouchermanager.service.voucher.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.UUID;


@Controller
@Slf4j
@RequestMapping("/admin/vouchers")
@RequiredArgsConstructor
public class WebVoucherController {

    private final VoucherService voucherService;

    @GetMapping("/{id}")
    String findById(@PathVariable String id, Model model) {
        Voucher voucher = voucherService.findById(UUID.fromString(id));
        model.addAttribute("voucher", voucher);

        return "voucher_one.html";
    }

    @GetMapping("")
    String findAll(Model model) {
        List<Voucher> vouchers = voucherService.findAll();
        model.addAttribute("vouchers", vouchers);

        return "voucher_list.html";
    }

    @GetMapping("/create")
    String getCreateForm() {
        return "voucher_create.html";
    }

    @PostMapping("/create")
    String create(@ModelAttribute CreateVoucherDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("code", "바우처 입력 정보가 잘못되었습니다.");
            return "voucher_exception.html";
        }
        if (dto.getVoucherType() == 2 && dto.getValue() > 100) {
            model.addAttribute("code", "percent는 100%를 넘어갈 수 없습니다.");
            return "voucher_exception.html";
        }
        Voucher voucher = voucherService.create(dto);
        redirectAttributes.addAttribute("id", voucher.getId());
        return "redirect:/admin/vouchers/{id}";
    }

    @GetMapping("/{id}/edit")
    String getUpdateForm(@PathVariable String id, Model model) {
        Voucher voucher = voucherService.findById(UUID.fromString(id));
        model.addAttribute("voucher", voucher);
        return "voucher_edit.html";
    }

    @PutMapping("/{id}/edit")
    String update(@PathVariable String id, @ModelAttribute UpdateVoucherDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("code", "바우처 입력 정보가 잘못되었습니다.");
            return "voucher_exception.html";
        }
        if (dto.getVoucherType() == 2 && dto.getValue() > 100) {
            model.addAttribute("code", "percent는 100%를 넘어갈 수 없습니다.");
            return "voucher_exception.html";
        }
        voucherService.update(UUID.fromString(id), dto);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/admin/vouchers/{id}";

    }

    @DeleteMapping("/{id}")
    String delete(@PathVariable String id) {
        voucherService.deleteById(UUID.fromString(id));
        return "redirect:/admin/vouchers";
    }

    @ExceptionHandler
    String ExceedVoucherPercent(InputMismatchException e, Model model) {
        model.addAttribute("code", e.getMessage());
        return "voucher_exception.html";
    }

    @ExceptionHandler
    String CommonException(Exception e, Model model) {
        model.addAttribute("code", "잘못된 접근입니다.");
        return "voucher_exception.html";
    }
}
