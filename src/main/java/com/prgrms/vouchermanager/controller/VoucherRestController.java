package com.prgrms.vouchermanager.controller;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.dto.VoucherResponse;
import com.prgrms.vouchermanager.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.prgrms.vouchermanager.dto.VoucherRequest.*;
import static com.prgrms.vouchermanager.dto.VoucherResponse.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/vouchers")
public class VoucherRestController {
    private final VoucherService service;

    @GetMapping
    public List<Voucher> vouchers() {
        return service.findAll();
    }

    @GetMapping("/{voucherId}")
    public Voucher voucher(@PathVariable UUID voucherId) {
        return service.findById(voucherId);
    }

    @PostMapping("/create")
    public VoucherDetailResponse create(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = service.create(voucherCreateRequest);
        return VoucherResponse.toDetailVoucher(voucher);
    }

    @GetMapping("/{voucherId}/delete")
    public int delete(@PathVariable UUID voucherId) {
        return service.delete(voucherId);
    }

    @GetMapping("/{voucherId}/findById")
    public Voucher findById(@PathVariable UUID voucherId) {
        return service.findById(voucherId);
    }

    @GetMapping("/findByCondition")
    public List<Voucher> findByCondition(@RequestBody VoucherType voucherType,
                                  @RequestBody int startYear,
                                  @RequestBody int startMonth,
                                  @RequestBody int endYear,
                                  @RequestBody int endMonth) {
        return service.findByCondition(voucherType, startYear, startMonth, endYear, endMonth);
    }
}
