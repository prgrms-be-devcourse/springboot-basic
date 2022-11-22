package org.prgrms.springorder.domain.voucher.api;

import java.util.List;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.api.response.VoucherCreateResponse;
import org.prgrms.springorder.domain.voucher.api.response.VoucherResponse;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(
        VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<?> getAllVouchers() {
        List<VoucherResponse> voucherResponses = voucherService.findAll().stream()
            .map(voucher -> new VoucherResponse(voucher.getVoucherId(),
                voucher.getAmount(),
                voucher.getCustomerId(),
                voucher.getCreatedAt(), voucher.getVoucherType()))
            .toList();

        return ResponseEntity.ok(voucherResponses);
    }

    @PostMapping
    public ResponseEntity<?> createVoucher(@RequestBody VoucherCreateRequest request) {
        Voucher voucher = voucherService.createVoucher(request);
        return new ResponseEntity<>(new VoucherCreateResponse(voucher.getVoucherId()),
            HttpStatus.CREATED);
    }

}
