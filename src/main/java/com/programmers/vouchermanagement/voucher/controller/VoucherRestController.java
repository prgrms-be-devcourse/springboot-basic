package com.programmers.vouchermanagement.voucher.controller;

import com.programmers.vouchermanagement.voucher.controller.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Profile("api")
@RequestMapping("/api/v1/vouchers")
@RestController
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseEntity<VoucherResponse> create(
            @RequestBody
            CreateVoucherRequest createVoucherRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(voucherService.create(createVoucherRequest));
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> readById(
            @PathVariable("voucherId")
            UUID voucherId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voucherService.readById(voucherId));
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> delete(
            @PathVariable("voucherId")
            UUID voucherId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voucherService.delete(voucherId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        voucherService.deleteAll();
        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping("/{voucherId}")
    public ResponseEntity<VoucherResponse> update(
            @PathVariable("voucherId")
            UUID voucherId,
            @RequestBody
            CreateVoucherRequest createVoucherRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(voucherService.update(voucherId, createVoucherRequest));
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> searchAll( //TODO: make Object
            @RequestParam(name = "filter", defaultValue = "all")
            String filter,
            @RequestParam(name = "from", required = false)
            LocalDate from,
            @RequestParam(name = "to", required = false)
            LocalDate to,
            @RequestParam(name = "type-name", required = false)
            String typeName
    ) {
        if (filter.equals("all"))
            return ResponseEntity.status(HttpStatus.OK)
                    .body(voucherService.readAll());

        if (filter.equals("created-at") && from != null) { //TODO: validate date
            LocalDateTime fromDateTime = LocalDateTime.of(
                    from,
                    LocalTime.of(0, 0, 0));

            LocalDateTime toDateTime = LocalDateTime.of(
                    to == null ? LocalDate.now() : to,
                    LocalTime.of(23, 59, 59));

            return ResponseEntity.status(HttpStatus.OK)
                    .body(voucherService.readAllByCreatedAt(fromDateTime, toDateTime));
        }

        if (filter.equals("type") && typeName != null)
            return ResponseEntity.status(HttpStatus.OK)
                    .body(voucherService.readAllByType(typeName));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.emptyList()); //TODO: apply ControllerAdvice
    }
}
