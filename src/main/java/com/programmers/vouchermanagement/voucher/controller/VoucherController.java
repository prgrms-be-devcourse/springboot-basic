package com.programmers.vouchermanagement.voucher.controller;

import com.programmers.vouchermanagement.voucher.domain.Voucher;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public VoucherResponse create(CreateVoucherRequest createVoucherRequest) {
        return voucherService.create(createVoucherRequest);
    }

    @GetMapping
    @ResponseBody
    public List<VoucherResponse> readAll() {
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

    @PutMapping
    @ResponseBody
    public void update(Voucher voucher) {
        voucherService.update(voucher);
    }

}
