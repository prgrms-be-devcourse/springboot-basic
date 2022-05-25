package org.devcourse.voucher.application.voucher.controller.api;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.model.VoucherType;
import org.devcourse.voucher.application.voucher.service.VoucherService;
import org.devcourse.voucher.core.utils.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/voucher")
public class RestVoucherController {

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("")
    public ApiResponse<Voucher> postCreateVoucher(@RequestBody VoucherRequest voucherRequest) {
        Voucher voucher = voucherService.createVoucher(voucherRequest.voucherType(), voucherRequest.price());
        return ApiResponse.ok(OK, voucher);
    }

    @GetMapping("")
    public ApiResponse<Page<Voucher>> getVoucherList(Pageable pageable) {
        Page<Voucher> vouchers = voucherService.recallAllVoucher(pageable);
        return ApiResponse.ok(OK, vouchers);
    }

    @GetMapping("/{id}")
    public ApiResponse<Voucher> getVoucherById(@PathVariable String id) {
        Voucher voucher = voucherService.recallVoucherById(UUID.fromString(id));
        return ApiResponse.ok(OK, voucher);
    }

    @PutMapping("/{id}")
    public ApiResponse<Voucher> putUpdateVoucher(@PathVariable String id, @RequestBody VoucherRequest voucherRequest) {
        Voucher voucher = voucherService.updateVoucher(
                UUID.fromString(id), voucherRequest
        );
        return ApiResponse.ok(OK, voucher);
    }
}
