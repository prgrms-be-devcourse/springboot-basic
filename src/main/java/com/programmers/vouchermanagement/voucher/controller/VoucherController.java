package com.programmers.vouchermanagement.voucher.controller;

import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherDto;
import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Profile({"api", "console"})
@RequestMapping("/api/v1/vouchers")
@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    @ResponseBody
    public VoucherDto create(CreateVoucherRequest createVoucherRequest) {
        return voucherService.create(createVoucherRequest);
    }

    @GetMapping
    @ResponseBody
    public List<VoucherDto> readAll() {
        return voucherService.readAll();
    }

    @GetMapping("/{voucherId}")
    @ResponseBody
    public void readById(@PathVariable("voucherId") UUID voucherId) {
        voucherService.readById(voucherId);
    }

    @DeleteMapping("/{voucherId}")
    @ResponseBody
    public void delete(@PathVariable("voucherId") UUID voucherId) {
        voucherService.delete(voucherId);
    }

    @DeleteMapping
    @ResponseBody
    public void deleteAll() {
        voucherService.deleteAll();
    }

    @PutMapping("/{voucherId}")
    @ResponseBody
    public void update(@PathVariable("voucherId") UUID voucherId, CreateVoucherRequest createVoucherRequest) {
        voucherService.update(voucherId, createVoucherRequest);
    }

    @GetMapping("/datetime")
    @ResponseBody
    public List<VoucherDto> readAllByCreatedAt(@RequestParam(name = "from") LocalDate from, @RequestParam(name = "to") LocalDate to) {
        LocalDateTime fromDateTime = LocalDateTime.of(from, LocalTime.of(0, 0, 0));
        LocalDateTime toDateTime = LocalDateTime.of(to, LocalTime.of(23, 59, 59));
        return voucherService.readAllByCreatedAt(fromDateTime, toDateTime);
    }
}
