package org.prgms.kdtspringvoucher.voucher.controller;

import com.fasterxml.jackson.core.JsonToken;
import org.prgms.kdtspringvoucher.voucher.domain.Voucher;
import org.prgms.kdtspringvoucher.voucher.domain.VoucherType;
import org.prgms.kdtspringvoucher.voucher.dto.CreateVoucherRequest;
import org.prgms.kdtspringvoucher.voucher.dto.UpdateVoucherRequest;
import org.prgms.kdtspringvoucher.voucher.service.VoucherService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/vouchers", produces = {MediaType.APPLICATION_JSON_VALUE})
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public List<Voucher> getVouchers(@RequestParam Optional<VoucherType> voucherType, @RequestParam Optional<LocalDateTime> from, @RequestParam Optional<LocalDateTime> to) {
        List<Voucher> vouchers = voucherService.getVouchers(voucherType.orElse(null), from.orElse(null), to.orElse(null));
        return vouchers;
    }

    @PostMapping
    public Voucher createVoucher(@RequestBody Optional<CreateVoucherRequest> createVoucherRequest) {
        CreateVoucherRequest request = createVoucherRequest.orElse(null);
        return voucherService.createVoucher(request.getVoucherType(), request.getAmount());
    }

    @GetMapping("{voucherId}")
    public Voucher getVouchersById(@PathVariable UUID voucherId) {
        return voucherService.getVoucherById(voucherId).orElse(null);
    }

    @DeleteMapping("{voucherId}")
    public void deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
    }
}
