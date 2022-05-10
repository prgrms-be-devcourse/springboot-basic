package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class VoucherApiController {

    private final JdbcVoucherService voucherService;

    public VoucherApiController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/api/vouchers")
    public List<Voucher> getVouchers() {
        return voucherService.getAllVouchers();
    }

    @PostMapping("/api/vouchers")
    public Voucher createVouchers(@RequestBody VoucherCreateRequestDto requestDto) {
        Voucher voucher = null;
        if (requestDto.getType().equals("fixed")) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), requestDto.getDiscountPrice());
        } else if (requestDto.getType().equals("percent")) {
            voucher = new PercentAmountVoucher(UUID.randomUUID(), requestDto.getDiscountPercent());
        }

        voucherService.save(voucher);
        return voucher;
    }

}
