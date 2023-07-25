package org.weekly.weekly.voucher.controller.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.weekly.weekly.voucher.dto.request.VoucherCreationRequest;
import org.weekly.weekly.voucher.dto.response.VoucherCreationResponse;
import org.weekly.weekly.voucher.dto.response.VouchersResponse;
import org.weekly.weekly.voucher.service.VoucherService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherAPIController {

    private final VoucherService voucherService;

    public VoucherAPIController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseEntity<VoucherCreationResponse> createVoucher(@RequestBody VoucherCreationRequest voucherCreationRequest) {
        VoucherCreationResponse voucherCreationResponse = voucherService.insertVoucher(voucherCreationRequest);
        return new ResponseEntity<>(voucherCreationResponse, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<VoucherCreationResponse>> getVouchers() {
        VouchersResponse vouchersResponse = voucherService.getVouchers();
        return new ResponseEntity<>(vouchersResponse.getResult(), HttpStatus.OK);
    }
}
