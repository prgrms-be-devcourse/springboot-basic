package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
    public List<Voucher> getVouchers(@RequestParam(required = false) String type) {
        if (type == null)
            return voucherService.getAllVouchers();

        if (type.equals("fixed")) {
            return voucherService.getAllFixVouchers();
        } else if (type.equals("percent")) {
            return voucherService.getAllPercentVouchers();
        }

        return null;
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
