package com.program.commandLine.controller.api;

import com.program.commandLine.model.voucher.Voucher;
import com.program.commandLine.model.voucher.VoucherType;
import com.program.commandLine.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping( "/api/v1/vouchers")
    @ResponseBody
    public List<Voucher> findVouchers(){
        return voucherService.getAllVoucher();
    }

    @GetMapping( "/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public ResponseEntity<Voucher> findVoucherById(@PathVariable("voucherId") UUID voucherId){
        var customer = voucherService.getVoucherById(voucherId);
        return customer.map(v -> ResponseEntity.ok(v)).orElse(ResponseEntity.notFound().build());
    }

}
