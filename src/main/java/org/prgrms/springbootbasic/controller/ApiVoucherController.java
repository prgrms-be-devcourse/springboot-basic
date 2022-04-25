package org.prgrms.springbootbasic.controller;

import org.prgrms.springbootbasic.dto.VoucherListResponse;
import org.prgrms.springbootbasic.service.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiVoucherController {

    private final VoucherService voucherService;

    public ApiVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public VoucherListResponse findAllVouchers() {
        var voucherDTOS = voucherService.findAll();
        return new VoucherListResponse(voucherDTOS);
    }

    @GetMapping("/api/v1/vouchers/{voucherType}")
    public VoucherListResponse findVoucherUsingType(
        @PathVariable("voucherType") VoucherType voucherType) {
        return new VoucherListResponse(
            DtoConverter.toVoucherDTOs(voucherService.findVoucherUsingType(voucherType)));
    }

}
