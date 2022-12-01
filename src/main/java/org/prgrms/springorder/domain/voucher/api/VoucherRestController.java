package org.prgrms.springorder.domain.voucher.api;

import java.time.LocalDateTime;
import java.util.UUID;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.api.response.VoucherCreateResponse;
import org.prgrms.springorder.domain.voucher.api.response.VoucherResponse;
import org.prgrms.springorder.domain.voucher.api.response.VoucherResponses;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
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
    public ResponseEntity<VoucherResponses> getAllVouchers(
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime startDate,
        @RequestParam(required = false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDateTime endDate,
        @RequestParam(required = false) VoucherType voucherType
    ) {
        
        VoucherResponses voucherResponses = voucherService.findAllBy(startDate, endDate,
            voucherType);

        return ResponseEntity.ok(voucherResponses);
    }

    @PostMapping// contentType을 반드시 정의하고 - 스펙을 보여줘서 약속해야한다. - 표준
    public ResponseEntity<VoucherCreateResponse> createVoucher(
        @RequestBody VoucherCreateRequest request) {
        Voucher voucher = voucherService.createVoucher(request);

        // prg - post redirect get - hateoas
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
