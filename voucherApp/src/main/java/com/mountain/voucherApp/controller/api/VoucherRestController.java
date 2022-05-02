package com.mountain.voucherApp.controller.api;

import com.mountain.voucherApp.dto.VoucherCreateDto;
import com.mountain.voucherApp.model.VoucherEntity;
import com.mountain.voucherApp.service.VoucherAppService;
import com.mountain.voucherApp.service.voucher.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {

    private final VoucherService voucherService;
    private final VoucherAppService voucherAppService;

    @Autowired
    public VoucherRestController(VoucherService voucherService, VoucherAppService voucherAppService) {
        this.voucherService = voucherService;
        this.voucherAppService = voucherAppService;
    }

    @GetMapping
    public List<VoucherEntity> vouchers() {
        return voucherService.findAll();
    }

    @PostMapping
    public ResponseEntity createVoucher(@RequestBody VoucherCreateDto voucherCreateDto) {
        if (!voucherAppService.create(voucherCreateDto)) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/{voucherId}")
    public ResponseEntity deleteByVoucherId(@PathVariable String voucherId) {
        voucherService.deleteById(UUID.fromString(voucherId));
        return new ResponseEntity(HttpStatus.OK);
    }
}
