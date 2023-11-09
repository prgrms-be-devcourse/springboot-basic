package com.programmers.vouchermanagement.voucher.controller;

import com.programmers.vouchermanagement.voucher.controller.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.controller.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
        VoucherResponse voucherResponse = voucherService.create(createVoucherRequest);
        return ResponseEntity.created(URI.create("/customers/" + voucherResponse.id()))
                .body(voucherResponse);
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
    public ResponseEntity<List<VoucherResponse>> readAll(
            //TODO: make Object and Validate!
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
            LocalTime localTime = LocalTime.of(0, 0, 0);
            LocalDateTime fromDateTime = LocalDateTime.of(
                    from,
                    localTime);

            LocalDateTime toDateTime = LocalDateTime.of(
                    to == null ? LocalDate.now() : to.plusDays(1),
                    localTime);

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
