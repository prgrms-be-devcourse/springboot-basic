package org.programmers.kdtspring.controller.api;

import org.programmers.kdtspring.dto.CreateVoucherRequest;
import org.programmers.kdtspring.dto.ResponseVoucher;
import org.programmers.kdtspring.dto.UpdateVoucherRequest;
import org.programmers.kdtspring.entity.voucher.Voucher;
import org.programmers.kdtspring.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseVoucher>> findAllVouchers() {
        List<ResponseVoucher> response = voucherService.getVouchers().stream().map(
                voucher -> new ResponseVoucher(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount(), voucher.getCustomerId())
        ).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/voucher")
    public ResponseEntity<ResponseVoucher> findVoucher(@RequestParam("voucherId") UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId).get();
        ResponseVoucher response = new ResponseVoucher(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount(), voucher.getCustomerId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{voucherType}")
    public ResponseEntity<List<ResponseVoucher>> getVoucherByType(@PathVariable String voucherType) {
        List<Voucher> vouchersByType = voucherService.getVouchersByType(voucherType);
        List<ResponseVoucher> response = new ArrayList<>();
        for (Voucher voucher : vouchersByType) {
            response.add(new ResponseVoucher(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount(), voucher.getCustomerId()));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseVoucher> createVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        UUID voucherId = createVoucherRequest.getVoucherType().toString().equalsIgnoreCase("FixedAmountVoucher") ?
                voucherService.createVoucher(createVoucherRequest.getVoucherType().toString(), createVoucherRequest.getAmount()) :
                voucherService.createVoucher(createVoucherRequest.getVoucherType().toString(), createVoucherRequest.getPercent());
        Optional<Voucher> voucher = voucherService.getVoucher(voucherId);

        ResponseVoucher response = new ResponseVoucher(voucher.get().getVoucherId(), voucher.get().getVoucherType(), voucher.get().getDiscount(), voucher.get().getCustomerId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{voucherId}")
    public ResponseEntity<ResponseVoucher> updateVoucher(
            @RequestBody UpdateVoucherRequest updateVoucherRequest,
            @PathVariable UUID voucherId) {

        Voucher voucher = voucherService.allocateVoucher(voucherId, UUID.fromString(updateVoucherRequest.getCustomerId()));
        System.out.println(updateVoucherRequest.getCustomerId());
        ResponseVoucher response = new ResponseVoucher(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscount(), voucher.getCustomerId());
        System.out.println(response.getCustomerId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteSingleVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.removeVoucher(voucherId);

        return ResponseEntity.noContent().build();
    }
}