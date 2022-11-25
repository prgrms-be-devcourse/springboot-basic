package org.prgrms.springorder.domain.voucher.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.api.response.VoucherCreateResponse;
import org.prgrms.springorder.domain.voucher.api.response.VoucherResponse;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(
        VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<VoucherResponse>> getAllVouchers(
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
        @RequestParam(required = false) VoucherType voucherType
    ) {

        List<VoucherResponse> voucherResponses = voucherService.findAllBy(startDate, endDate,
                voucherType).stream()
            .map(voucher -> new VoucherResponse(voucher.getVoucherId(),
                voucher.getAmount(),
                voucher.getCreatedAt(), voucher.getVoucherType()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(voucherResponses);
    }

    @PostMapping
    public ResponseEntity<VoucherCreateResponse> createVoucher(
        @RequestBody VoucherCreateRequest request) {
        Voucher voucher = voucherService.createVoucher(request);
        return new ResponseEntity<>(new VoucherCreateResponse(voucher.getVoucherId()),
            HttpStatus.CREATED);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> getById(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.findById(voucherId);

        return ResponseEntity.ok(new VoucherResponse(voucher.getVoucherId(),
            voucher.getAmount(),
            voucher.getCreatedAt(),
            voucher.getVoucherType()));
    }

}
