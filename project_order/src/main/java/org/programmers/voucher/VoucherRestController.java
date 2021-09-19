package org.programmers.voucher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public List<VoucherResponse> getVouchers() {
        List<Voucher> allVouchers = voucherService.getAllVouchers();
        return allVouchers.stream()
                .map(voucher -> mapToResponse(voucher))
                .collect(Collectors.toList());
    }

    @GetMapping("/{voucherId}")
    public VoucherResponse getVoucherById(@PathVariable("voucherId") UUID voucherId) {
        Voucher voucher = voucherService.getVoucher(voucherId);
        return mapToResponse(voucher);
    }

    @GetMapping("/{voucherType}")
    public List<VoucherResponse> getVouchersByType(@PathVariable("voucherType") VoucherType voucherType) {
        List<Voucher> vouchersByType = voucherService.getVouchersByType(voucherType);
        return vouchersByType.stream()
                .map(voucher -> mapToResponse(voucher))
                .collect(Collectors.toList());
    }

    @PostMapping("")
    public VoucherResponse createVoucher(@RequestBody VoucherRequest voucherRequest) {
        Voucher voucher = voucherService.createVoucher(voucherRequest.voucherType(), UUID.randomUUID(), voucherRequest.discountValue());
        return mapToResponse(voucher);
    }

    @DeleteMapping("/{voucherId}")
    public HttpStatus deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return HttpStatus.OK;
    }

    private VoucherResponse mapToResponse(Voucher voucher) {
        return new VoucherResponse(voucher.getVoucherId(), voucher.getDiscountValue(), voucher.getVoucherType(), voucher.getOwnerId());
    }

}
