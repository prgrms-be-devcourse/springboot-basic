package com.pppp0722.vouchermanagement.controller.api;

import com.pppp0722.vouchermanagement.controller.dto.CreateVoucherRequest;
import com.pppp0722.vouchermanagement.controller.dto.DeleteVoucherRequest;
import com.pppp0722.vouchermanagement.controller.dto.VoucherDto;
import com.pppp0722.vouchermanagement.voucher.model.Voucher;
import com.pppp0722.vouchermanagement.voucher.model.VoucherType;
import com.pppp0722.vouchermanagement.voucher.service.VoucherService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(
        VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public List<VoucherDto> getAllVouchers(
        @RequestParam(value = "type", required = false) VoucherType type) {
        List<Voucher> vouchers;
        if (type == null) {
            vouchers = voucherService.getAllVouchers();
        } else {
            vouchers = voucherService.getVouchersByType(type);
        }

        return vouchers
            .stream()
            .map(VoucherDto::from)
            .toList();
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public VoucherDto getVoucherById(@RequestParam(value = "id") UUID voucherId) {
        return VoucherDto.from(voucherService.getVoucherById(voucherId));
    }

    @PostMapping("/api/v1/vouchers")
    @ResponseBody
    public VoucherDto createVoucher(@RequestBody CreateVoucherRequest request) {
        return VoucherDto.from(
            voucherService.createVoucher(UUID.randomUUID(), request.type(), request.amount(),
                LocalDateTime.now(), request.memberId()));
    }

    @PostMapping("/api/v1/vouchers/delete/{voucherId}")
    @ResponseBody
    public VoucherDto deleteVoucher(@RequestBody DeleteVoucherRequest request) {
        return VoucherDto.from(
            voucherService.deleteVoucher(request.voucherId()));
    }
}
