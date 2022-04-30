package com.mountain.voucherApp.adapter.in.web.api;

import com.mountain.voucherApp.adapter.out.persistence.voucher.VoucherEntity;
import com.mountain.voucherApp.application.port.in.VoucherAppUseCase;
import com.mountain.voucherApp.application.port.in.VoucherCreateDto;
import com.mountain.voucherApp.application.service.VoucherService;
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
    private final VoucherAppUseCase voucherAppUseCase;

    @Autowired
    public VoucherRestController(VoucherService voucherService, VoucherAppUseCase voucherAppUseCase) {
        this.voucherService = voucherService;
        this.voucherAppUseCase = voucherAppUseCase;
    }

    @GetMapping
    public List<VoucherEntity> vouchers() {
        return voucherService.findAll();
    }

    @PostMapping
    public ResponseEntity createVoucher(@RequestBody VoucherCreateDto voucherCreateDto) {
        if (!voucherAppUseCase.create(voucherCreateDto)) {
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
