package org.prgrms.kdt.controller.voucher;

import org.prgrms.kdt.controller.ApiResponse;
import org.prgrms.kdt.service.voucher.VoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/v1/vouchers")
    public ApiResponse<List<VoucherDto>> findAll() {
        List<VoucherDto> vouchers = voucherService.findAll().stream()
            .map(VoucherDto::new).toList();

        return ApiResponse.OK(vouchers);
    }
}
