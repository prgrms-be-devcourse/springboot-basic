package com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher;


import com.programmers.kwonjoosung.springbootbasicjoosung.model.voucher.VoucherType;
import com.programmers.kwonjoosung.springbootbasicjoosung.service.VoucherService;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request.VoucherDto;
import com.programmers.kwonjoosung.springbootbasicjoosung.controller.voucher.request.CreateVoucherRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequestMapping(value = "/api/v1/voucher",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
@RestController
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping()
    public ResponseEntity<VoucherDto> createVoucher(@RequestBody CreateVoucherRequest request) {
        return ResponseEntity.ok(voucherService.saveVoucher(request));
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<VoucherDto>> getAllVoucherList() {
        return ResponseEntity.ok(voucherService.getAllVouchers());
    }

    @PostMapping(value = "/list")
    public ResponseEntity<List<VoucherDto>> findVouchersByType(@RequestBody VoucherType voucherType) { // 다양하게 검색할 수 있도록 만들어 보는 것도 좋을 듯
        return ResponseEntity.ok(voucherService.findVoucher(voucherType));
    }

    @GetMapping(value = "/{voucherId}")
    public ResponseEntity<VoucherDto> findVoucherById(@PathVariable String voucherId) {
        return ResponseEntity.ok(voucherService.findVoucher(UUID.fromString(voucherId)));

    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity<Object> deleteVoucher(@PathVariable String voucherId) {
        voucherService.deleteVoucher(UUID.fromString(voucherId));
        return ResponseEntity.accepted().build();
    }
}
