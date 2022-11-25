package com.example.weeklymission3.controllers;

import com.example.weeklymission3.models.Voucher;
import com.example.weeklymission3.models.VoucherRequestDto;
import com.example.weeklymission3.models.VoucherResponseDto;
import com.example.weeklymission3.models.VoucherType;
import com.example.weeklymission3.services.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/voucher-list")
    public String vouchersPage(Model model) {
        List<VoucherResponseDto> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        return "voucher-list";
    }

    @GetMapping("/voucher-new")
    public String voucherAddPage() {
        return "voucher-new";
    }

    @PostMapping("/voucher-new")
    public String newVoucher(VoucherRequestDto requestDto) {
        voucherService.createVoucher(requestDto.voucherType(), requestDto.discount());
        return "redirect:/voucher-list";
    }

    @GetMapping("/voucher/{voucherId}")
    public String voucherDetailPage(@PathVariable("voucherId") String voucherId, Model model) {
        Optional<Voucher> voucher = voucherService.getVoucherById(UUID.fromString(voucherId));
        if (voucher.isEmpty()) {
            return "error";
        }
        model.addAttribute("voucher", voucher.get());
        return "voucher-detail";
    }

    @DeleteMapping("/voucher-delete")
    public String voucherDelete() {
        voucherService.deleteAllVouchers();
        return "redirect:/voucher-list";
    }

    @GetMapping("api/v1/voucher-list")
    @ResponseBody
    public List<VoucherResponseDto> getAllVoucher() {
        return voucherService.getAllVouchers();
    }

    @GetMapping("api/v1/voucher-list/{startDate}/{endDate}")
    @ResponseBody
    public List<Voucher> getVouchersByTime(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        LocalDateTime start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE).atStartOfDay();
        LocalDateTime end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE).atStartOfDay();
        return voucherService.getVouchersByTime(start, end);
    }

    @GetMapping("api/v1/voucher-list/{type}")
    @ResponseBody
    public List<Voucher> getVoucherByType(@PathVariable("type") String type) {
        return voucherService.getVouchersByType(VoucherType.checkVoucherType(type));
    }

    @PostMapping("api/v1/voucher-new")
    @ResponseBody
    public Voucher createVoucher(VoucherRequestDto requestDto) {
        String voucherType = VoucherType.transferVoucherType(VoucherType.checkVoucherType(requestDto.voucherType()));
        return voucherService.createVoucher(voucherType, requestDto.discount());
    }

    @GetMapping("api/v1/voucher/{voucherId}")
    @ResponseBody
    public ResponseEntity<Voucher> getVoucherById(@PathVariable("voucherId") String voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucherById(UUID.fromString(voucherId));
        return voucher.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("api/v1/voucher-delete")
    public ResponseEntity deleteAllVoucher() {
        voucherService.deleteAllVouchers();
        return ResponseEntity.ok("delete all voucher");
    }

}
