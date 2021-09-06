package org.prgms.w3d1.controller;

import org.prgms.w3d1.model.voucher.Voucher;
import org.prgms.w3d1.model.voucher.VoucherFactory;
import org.prgms.w3d1.model.voucher.VoucherType;
import org.prgms.w3d1.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // 전체 조회기능
    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public List<Voucher> getAllVouchers() {
        return voucherService.findAll();
    }

    //특정 아이디 조회
    @GetMapping("/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public Voucher getVoucher(@PathVariable("voucherId") UUID voucherId) {
        return voucherService.getVoucher(voucherId);
    }

    // 바우처 타입별 조회
    @GetMapping("api/v1/vouchers/type/{voucherType}")
    @ResponseBody
    public List<Voucher> getVoucherByType(
        @PathVariable String voucherType) {
        return voucherService.getVouchersByType(VoucherType.getType(voucherType));
    }

    // 바우처 생성
    @PostMapping("api/v1/vouchers/{voucherId}")
    @ResponseBody
    public Voucher createVoucher(
        @PathVariable("voucherId") UUID voucherId,
        @RequestParam(value = "value") long value,
        @RequestParam(value = "type") String voucherType) {
        Voucher voucher = VoucherFactory.of(voucherId, value, voucherType);
        voucherService.saveVoucher(voucher);
        return voucher;
    }

    // 바우처 삭제
    @DeleteMapping("api/v1/vouchers/{voucherId}")
    @ResponseBody
    public void deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }
}
