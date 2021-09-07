package com.programmers.kdtspringorder.voucher.controller;

import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    /**
     * 전체 조회 기능
     * @return 바우처 리스트
     */
    @GetMapping("/vouchers")
    public List<Voucher> findVouchers(){
        return voucherService.findAll();
    }

    /**
     * 바우처 추가기능
     * @param request
     * @return 생성된 바우처
     */
    @PostMapping("/vouchers/new")
    public Voucher createVoucher(@ModelAttribute VoucherCreateRequest request) {
        return voucherService.createVoucher(request.getVoucherType(), request.getValue());
    }

    /**
     * 바우처 삭제기능
     * @param voucherId
     */
    @DeleteMapping("/vouchers/{voucherId}")
    public void deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

    /**
     * 바우처 아이디로 조회 기능
     * @param voucherId
     * @return 조회한 아이디
     */
    @GetMapping("/vouchers/{voucherId}")
    public Voucher findVoucherById(@PathVariable UUID voucherId) {
        return voucherService.findByID(voucherId);
    }
}
