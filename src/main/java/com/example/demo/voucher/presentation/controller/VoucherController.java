package com.example.demo.voucher.presentation.controller;

import com.example.demo.voucher.application.VoucherService;
import com.example.demo.voucher.presentation.controller.dto.CreateVoucherForm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("vouchers", voucherService.listVouchers());

        return "voucher/list";
    }

    @GetMapping("/{id}")
    public String findById(Model model, @PathVariable("id") String id) {
        model.addAttribute("voucher", voucherService.getVoucher(UUID.fromString(id)));

        return "voucher/details";
    }

    @GetMapping("/new")
    public String viewNewVoucherPage() {
        return "voucher/new";
    }

    @PostMapping("/new")
    public String createVoucher(CreateVoucherForm voucherForm) {
        voucherService.createVoucher(voucherForm.voucherType(), voucherForm.value());
        return "redirect:/vouchers";
    }

    @DeleteMapping
    @ResponseBody
    public ResponseEntity deleteAll() {
        voucherService.deleteAll();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
