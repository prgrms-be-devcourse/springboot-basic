package org.prgrms.kdt.domain.voucher.controller;

import org.prgrms.kdt.domain.common.model.PageResponse;
import org.prgrms.kdt.domain.voucher.exception.VoucherDataException;
import org.prgrms.kdt.domain.voucher.model.Voucher;
import org.prgrms.kdt.domain.voucher.model.VoucherType;
import org.prgrms.kdt.domain.voucher.request.VoucherCreateRequest;
import org.prgrms.kdt.domain.voucher.request.VoucherSearchRequest;
import org.prgrms.kdt.domain.voucher.request.VoucherUpdateRequest;
import org.prgrms.kdt.domain.voucher.response.VoucherResponse;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdt.domain.common.exception.ExceptionType.NOT_SAVED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<List<VoucherResponse>>> getAllVouchers(VoucherSearchRequest searchRequest) {
        List<Voucher> vouchers = voucherService.getAllVouchers(searchRequest);
        List<VoucherResponse> voucherResponseList = vouchers.stream()
                .map(VoucherResponse::new).toList();
        return ResponseEntity.ok()
                .body(new PageResponse<>(voucherResponseList, voucherResponseList.size()));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createVoucher(@Valid @RequestBody VoucherCreateRequest createRequest) {
        Voucher voucher = createRequest.toEntity();
        voucherService.save(voucher);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> getVoucher(@PathVariable("voucherId") UUID voucherId) {
        Optional<Voucher> voucher = voucherService.getVoucherById(voucherId);
        return voucher.map(value -> ResponseEntity.ok().body(value))
                .orElseThrow(() -> new VoucherDataException(NOT_SAVED));
    }

    @PutMapping("/{voucherId}")
    public ResponseEntity<HttpStatus> modifyVoucher(
            @Valid @RequestBody VoucherUpdateRequest updateRequest,
            @PathVariable("voucherId") UUID voucherId) {
        voucherService.update(voucherId,
                updateRequest.getVoucherType(),
                updateRequest.getDiscountValue());
        return new ResponseEntity<>(OK);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<HttpStatus> removeVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.remove(voucherId);
        return new ResponseEntity<>(OK);
    }
}
