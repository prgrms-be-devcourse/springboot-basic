package com.programmers.part1.order.voucher;

import com.programmers.part1.domain.voucher.CreateRequestVoucher;
import com.programmers.part1.domain.voucher.Voucher;
import com.programmers.part1.domain.voucher.VoucherType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
@RequiredArgsConstructor
public class VoucherRestController {

    private final VoucherService voucherService;

    // 모든 바우처 조회 및 조건별 조회
    @GetMapping
    public ResponseEntity<List<Voucher>> viewVoucherList() {
        List<Voucher> voucherList = voucherService.getAllVoucher();
        return ResponseEntity.ok(voucherList);
    }

    // 아이디로 조회
    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> viewVoucher(@PathVariable("voucherId") UUID voucherId) {
        return voucherService.getVoucherById(voucherId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 바우처 생성
    @PostMapping("/new")
    public ResponseEntity<Voucher> createVoucher(@RequestBody CreateRequestVoucher createRequestVoucher) {
        Voucher voucher = voucherService.createVoucher(VoucherType.valueOf(createRequestVoucher.getVoucherType()), createRequestVoucher.getAmount());
        return ResponseEntity.ok(voucher);
    }

    // 바우처 삭제
    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return ResponseEntity.noContent().build();
    }

}
