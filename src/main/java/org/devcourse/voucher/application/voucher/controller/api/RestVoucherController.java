package org.devcourse.voucher.application.voucher.controller.api;

import org.devcourse.voucher.application.voucher.controller.dto.VoucherRequest;
import org.devcourse.voucher.application.voucher.controller.dto.VoucherResponse;
import org.devcourse.voucher.application.voucher.service.VoucherService;
import org.devcourse.voucher.core.utils.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/voucher")
public class RestVoucherController {

    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("")
    @ResponseStatus(CREATED)
    public ApiResponse<VoucherResponse> postCreateVoucher(@RequestBody VoucherRequest voucherRequest) {
        VoucherResponse voucher = voucherService.createVoucher(voucherRequest.getVoucherType(), voucherRequest.getDiscount());
        return ApiResponse.ok(CREATED, voucher);
    }

    @GetMapping("")
    public ApiResponse<List<VoucherResponse>> getVoucherList(Pageable pageable) {
        List<VoucherResponse> vouchers = voucherService.recallAllVoucher(pageable);
        return ApiResponse.ok(OK, vouchers);
    }

    @GetMapping("/{id}")
    public ApiResponse<VoucherResponse> getVoucherById(@PathVariable String id) {
        VoucherResponse voucher = voucherService.recallVoucherById(UUID.fromString(id));
        return ApiResponse.ok(OK, voucher);
    }

    @PatchMapping("/{id}")
    public ApiResponse<VoucherResponse> patchUpdateVoucher(@PathVariable String id, @RequestBody VoucherRequest voucherRequest) {
        VoucherResponse voucher = voucherService.updateVoucher(
                UUID.fromString(id),
                voucherRequest.getDiscount()
        );
        return ApiResponse.ok(OK, voucher);
    }
}
