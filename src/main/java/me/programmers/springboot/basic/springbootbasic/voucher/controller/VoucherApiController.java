package me.programmers.springboot.basic.springbootbasic.voucher.controller;

import me.programmers.springboot.basic.springbootbasic.voucher.dto.VoucherCreateRequestDto;
import me.programmers.springboot.basic.springbootbasic.voucher.model.FixedAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.PercentAmountVoucher;
import me.programmers.springboot.basic.springbootbasic.voucher.model.Voucher;
import me.programmers.springboot.basic.springbootbasic.voucher.service.JdbcVoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/vouchers")
@RestController
public class VoucherApiController {

    private final JdbcVoucherService voucherService;

    public VoucherApiController(JdbcVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<Voucher>> getVouchers(@RequestParam(required = false) String type) {
        if (type == null)
            return ResponseEntity.ok(voucherService.getAllVouchers());

        if (type.equals("fixed")) {
            return ResponseEntity.ok(voucherService.getAllFixVouchers());
        } else if (type.equals("percent")) {
            return ResponseEntity.ok(voucherService.getAllPercentVouchers());
        }

        return ResponseEntity.ok(List.of());
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable UUID voucherId) {
        return ResponseEntity.ok(voucherService.getVoucherById(voucherId));
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<UUID> deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
        return ResponseEntity.ok(voucherId);
    }

    @PostMapping
    public ResponseEntity<UUID> createVouchers(@RequestBody VoucherCreateRequestDto requestDto) {
        Voucher voucher = null;
        if (requestDto.getType().equals("fixed")) {
            voucher = new FixedAmountVoucher(UUID.randomUUID(), requestDto.getDiscountPrice());
        } else if (requestDto.getType().equals("percent")) {
            voucher = new PercentAmountVoucher(UUID.randomUUID(), requestDto.getDiscountPercent());
        }

        voucherService.save(voucher);
        return ResponseEntity.ok(voucher.getVoucherId());
    }

}
