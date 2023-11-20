package org.prgrms.vouchermanager.handler.voucher;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.common.ApiResponse;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.MenuType;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
public class ApiVoucherController {
    private final VoucherService voucherService;
    @GetMapping("/")
    public ResponseEntity<List<Voucher>> findAll(){
        return new ResponseEntity<List<Voucher>>(voucherService.findAllVoucher(),HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<Voucher> createVoucher(@RequestBody MenuType menuType){
        if (menuType == MenuType.FIXED) {
            return new ResponseEntity<Voucher>(voucherService.createVoucher(new FixedAmountVoucher(UUID.randomUUID(), 10, MenuType.FIXED)), HttpStatus.OK);
        } else {
            return new ResponseEntity<Voucher>(voucherService.createVoucher(new PercentDiscountVoucher(UUID.randomUUID(), 10, MenuType.PERCENT)), HttpStatus.OK);
        }
    }
}
