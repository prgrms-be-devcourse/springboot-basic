package com.program.commandLine.controller.api;

import com.program.commandLine.controller.view.VoucherViewController;
import com.program.commandLine.model.VoucherInputData;
import com.program.commandLine.model.voucher.Voucher;
import com.program.commandLine.model.voucher.VoucherType;
import com.program.commandLine.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class VoucherRestController {

    private final VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherRestController.class);

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping( "/api/v1/vouchers")
    public List<Voucher> findVouchers(){
        return voucherService.getAllVoucher();
    }

    @PostMapping("/api/v1/vouchers")
    public Voucher createVoucher(@RequestBody VoucherInputData voucherInputData){
        return voucherService.createVoucher(voucherInputData);
    }

    @GetMapping( "/api/v1/vouchers/{voucherId}")
    public ResponseEntity<Voucher> findVoucherById(@PathVariable("voucherId") UUID voucherId){
        var customer = voucherService.getVoucherById(voucherId);

        return customer.map(v -> ResponseEntity.ok(v)).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping( "/api/v1/vouchers/{voucherId}")
    public void deleteVoucher(@PathVariable("voucherId") UUID voucherId){
        voucherService.deleteVoucher(voucherId);
    }

    @GetMapping("api/v1/vouchers/type={voucherType}")
    public List<Voucher> productList(@PathVariable("voucherType") Optional<VoucherType> voucherType){
        return voucherType
                .map(voucherService::getVouchersByType)
                .orElse(List.of());
    }
}
