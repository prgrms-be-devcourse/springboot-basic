package com.prgms.management.voucher.controller;

import com.prgms.management.common.dto.Response;
import com.prgms.management.common.dto.StatusCode;
import com.prgms.management.voucher.dto.VoucherRequest;
import com.prgms.management.voucher.dto.VoucherResponse;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/vouchers")
public class APIVoucherController {
    private final VoucherService voucherService;
    
    public APIVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    
    @GetMapping
    public Response voucherList() {
        List<Voucher> voucherList = voucherService.findVouchers();
        return new Response(
            StatusCode.OK, "바우처 조회 성공", voucherList.stream().map(VoucherResponse::of).toList()
        );
    }
    
    @PostMapping
    public Response voucherAdd(@RequestBody VoucherRequest request) {
        Voucher voucher = voucherService.addVoucher(request.toVoucher());
        return new Response(
            StatusCode.OK, "바우처 등록 성공", VoucherResponse.of(voucher)
        );
    }
    
    @GetMapping("{id}")
    public Response voucherDetail(@PathVariable("id") UUID id) {
        Voucher voucher = voucherService.findVoucherById(id);
        return new Response(
            StatusCode.OK, "바우처 조회 성공", VoucherResponse.of(voucher)
        );
    }
    
    @DeleteMapping("{id}")
    public Response voucherRemove(@PathVariable("id") UUID id) {
        voucherService.removeVoucherById(id);
        return new Response(StatusCode.OK, "바우처 삭제 성공", null);
    }
}
