package com.devcourse.springbootbasic.application.voucher.controller;

import com.devcourse.springbootbasic.application.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherDto registerVoucher(VoucherDto voucherDto) {
        return VoucherDto.of(
                voucherService.createVoucher(
                        VoucherDto.to(voucherDto)
                )
        );
    }

    public VoucherDto updateVoucher(VoucherDto voucherDto) {
        return VoucherDto.of(
                voucherService.updateVoucher(
                        VoucherDto.to(voucherDto)
                )
        );
    }

    public List<VoucherDto> voucherList() {
        return voucherService.findVouchers().stream()
                .map(VoucherDto::of)
                .toList();
    }

    public VoucherDto getVoucherById(UUID voucherId) {
        return VoucherDto.of(voucherService.findVoucherById(voucherId));
    }

    public List<VoucherDto> voucherListOfCustomer(UUID customerId) {
        return voucherService.findVouchersByCustomerId(customerId).stream()
                .map(VoucherDto::of)
                .toList();
    }

    public void unregisterVouchers() {
        voucherService.deleteAllVouchers();
    }

    public VoucherDto unregisterVoucherById(UUID voucherId) {
        return VoucherDto.of(voucherService.deleteVoucherById(voucherId));
    }

    public VoucherDto unregisterVoucherByCustomerIdAndVoucherId(UUID customerId, UUID voucherId) {
        return VoucherDto.of(voucherService.deleteVoucherCustomerByCustomerIdAndVoucherId(customerId, voucherId));
    }

}
