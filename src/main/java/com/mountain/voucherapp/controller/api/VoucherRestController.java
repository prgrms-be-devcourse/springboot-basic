package com.mountain.voucherapp.controller.api;

import com.mountain.voucherapp.dto.VoucherCreateDto;
import com.mountain.voucherapp.model.VoucherEntity;
import com.mountain.voucherapp.service.VoucherAppService;
import com.mountain.voucherapp.service.voucher.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;
    private final VoucherAppService voucherAppService;

    public VoucherRestController(VoucherService voucherService, VoucherAppService voucherAppService) {
        this.voucherService = voucherService;
        this.voucherAppService = voucherAppService;
    }

    @GetMapping
    public List<VoucherEntity> vouchers() {
        return voucherService.findAll();
    }

    @PostMapping
    public ResponseEntity<VoucherCreateDto> createVoucher(@RequestBody VoucherCreateDto voucherCreateDto)
            throws URISyntaxException {
        if (voucherAppService.create(voucherCreateDto)) {
            URI uri = new URI("/new-voucher-success");
            return ResponseEntity.created(uri).body(voucherCreateDto);
        }
        return ResponseEntity.ok(voucherCreateDto);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Void> deleteByVoucherId(@PathVariable String voucherId) {
        voucherService.deleteById(UUID.fromString(voucherId));
        return ResponseEntity.noContent().build();
    }
}
