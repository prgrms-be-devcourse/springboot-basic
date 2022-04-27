package com.blessing333.springbasic.web.api;

import com.blessing333.springbasic.domain.voucher.converter.VoucherPayloadConverter;
import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateForm;
import com.blessing333.springbasic.domain.voucher.dto.VoucherCreateFormPayload;
import com.blessing333.springbasic.domain.voucher.model.Voucher;
import com.blessing333.springbasic.domain.voucher.service.VoucherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RestVoucherController {
    private static final String API_URL = "/api/v1/vouchers";
    private final VoucherService voucherService;
    private final VoucherPayloadConverter converter;

    @GetMapping(API_URL)
    public List<VoucherInformation> loadAllVoucher(@RequestParam(required = false) String voucherTypeCode) {
        List<Voucher> vouchers;
        if (voucherTypeCode == null) {
            vouchers = voucherService.loadAllVoucher();
        } else {
            Voucher.VoucherType type = converter.toVoucherType(voucherTypeCode);
            vouchers = voucherService.loadVouchersByType(type);
        }
        return VoucherInformation.fromEntity(vouchers);
    }

    @GetMapping(API_URL + "/{voucherId}")
    public VoucherInformation loadVoucherById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.loadVoucherById(voucherId);
        return VoucherInformation.fromEntity(voucher);
    }

    @PostMapping(API_URL)
    public VoucherInformation registerNewVoucher(@RequestBody VoucherCreateFormPayload payload) {
        VoucherCreateForm form = converter.toCreateForm(payload);
        Voucher result = voucherService.registerVoucher(form);
        return VoucherInformation.fromEntity(result);
    }

    @DeleteMapping(API_URL + "/{voucherId}")
    public ResponseEntity<String> removeVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
        return ResponseEntity.ok().build();
    }
}
