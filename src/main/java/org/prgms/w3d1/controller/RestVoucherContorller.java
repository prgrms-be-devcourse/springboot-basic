package org.prgms.w3d1.controller;

import org.prgms.w3d1.controller.api.CreateVoucherRequest;
import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherFactory;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class RestVoucherContorller {

    private final VoucherService voucherService;

    public RestVoucherContorller(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // 전체 조회기능
    @GetMapping("/api/v1/vouchers")
    public List<Voucher> getAllVouchers() {
        return voucherService.findAll();
    }

    //특정 아이디 조회
    @GetMapping("/api/v1/vouchers/{voucherId}")
    public Voucher getVoucher(@PathVariable("voucherId") UUID voucherId) {
        return voucherService.getVoucher(voucherId);
    }

    // 바우처 타입별 조회
    @GetMapping(path = "/api/v1/vouchers", params = "type")
    public List<Voucher> getVoucherByType(
        @RequestParam("type") String type) {
        return voucherService.getVouchersByType(VoucherType.getVoucherType(type));
    }

    // 바우처 생성
    @PostMapping("/api/v1/vouchers/{voucherId}")
    public Voucher createVoucher (
            @PathVariable("voucherId") UUID voucherId, @RequestBody CreateVoucherRequest createVoucherRequest)
    {
        Voucher voucher = VoucherFactory.of(voucherId, createVoucherRequest.value(), createVoucherRequest.type());
        voucherService.saveVoucher(voucher);
        return voucher;
    }

    // 바우처 삭제
    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    public void deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteById(voucherId);
    }
}
