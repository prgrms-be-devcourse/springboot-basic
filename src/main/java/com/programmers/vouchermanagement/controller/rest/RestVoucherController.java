package com.programmers.vouchermanagement.controller.rest;

import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.VoucherDto;
import com.programmers.vouchermanagement.service.VoucherService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.programmers.vouchermanagement.controller.rest.ApiEndpoints.REST_CUSTOMER_BASE_URI;
import static com.programmers.vouchermanagement.controller.rest.ApiEndpoints.REST_VOUCHER_BASE_URI;

@RestController
@RequestMapping(REST_VOUCHER_BASE_URI)
public class RestVoucherController {
    private final VoucherService voucherService;

    public RestVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherDto.Response>> showAllVouchers() {
        final List<VoucherDto.Response> vouchers = voucherService.findAllVouchers().stream()
                .map(VoucherDto.Response::new).toList();
        return ResponseEntity.ok(vouchers);

    }

    @PostMapping
    public ResponseEntity<VoucherDto.Response> createVoucher(@ModelAttribute VoucherDto.CreateRequest voucherDto,
                                                             UriComponentsBuilder uriComponentsBuilder) {
        VoucherDto.Response voucher = new VoucherDto.Response(voucherService.createVoucher(voucherDto));
        URI locationUri = uriComponentsBuilder.path(REST_CUSTOMER_BASE_URI + "/{id}")
                .buildAndExpand(voucher.getId())
                .toUri();
        return ResponseEntity.created(locationUri).body(voucher);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteVoucher(@RequestParam String voucherId) {
        voucherService.deleteVoucher(UUID.fromString(voucherId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<VoucherDto.Response>> searchVoucher(
            @RequestParam(required = false) String voucherId,
            @RequestParam(required = false) VoucherType voucherType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        List<VoucherDto.Response> vouchers = new ArrayList<>();
        if (voucherId != null) {
            vouchers.add(new VoucherDto.Response(voucherService.findVoucherById(UUID.fromString(voucherId))));
        } else {
            vouchers = voucherService.findVoucherByTypeAndDates(voucherType, startDate, endDate).stream()
                    .map(VoucherDto.Response::new).toList();
        }
        return ResponseEntity.ok(vouchers);
    }
}
