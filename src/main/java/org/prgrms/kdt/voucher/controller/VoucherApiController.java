package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.controller.dto.SearchApiRequest;
import org.prgrms.kdt.voucher.controller.dto.CreateVoucherApiRequest;
import org.prgrms.kdt.voucher.controller.mapper.ControllerVoucherMapper;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.voucher.service.dto.VoucherDetailResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;

@RequestMapping("/api/vouchers")
@RestController
public class VoucherApiController {
    private final VoucherService voucherService;
    private final ControllerVoucherMapper mapper;

    public VoucherApiController(VoucherService voucherService, ControllerVoucherMapper mapper) {
        this.voucherService = voucherService;
        this.mapper = mapper;
    }

    @PostMapping
    public ResponseEntity<VoucherResponse> create(@RequestBody @Valid CreateVoucherApiRequest request) {
        VoucherResponse response = voucherService.createVoucher(mapper.convertRequest(request));

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.voucherId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoucherDetailResponse> findById(@PathVariable UUID id) {
        VoucherDetailResponse response = voucherService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<VoucherResponses> findAllBy(@ModelAttribute @Valid SearchApiRequest request) {
        VoucherResponses response = voucherService.findAll(mapper.convertRequest(request));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        voucherService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
