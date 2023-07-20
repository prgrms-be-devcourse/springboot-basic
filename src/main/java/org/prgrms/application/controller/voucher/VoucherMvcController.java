package org.prgrms.application.controller.voucher;

import org.prgrms.application.controller.voucher.request.VoucherRegisterRequest;
import org.prgrms.application.domain.voucher.VoucherDto;
import org.prgrms.application.domain.voucher.VoucherType;
import org.prgrms.application.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mvc/vouchers")
public class VoucherMvcController {

    private final VoucherService voucherService;

    public VoucherMvcController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public ResponseEntity<List<VoucherDto>> getVouchers(){
        List<VoucherDto> vouchers = voucherService.getVouchers();
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/voucherType")
    public ResponseEntity<List<VoucherDto>> getVouchersByType(@RequestParam String voucherType){
        List<VoucherDto> vouchers = voucherService.getVouchersByType(VoucherType.valueOf(voucherType));
        return ResponseEntity.ok(vouchers);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Long> createVoucher(@RequestBody VoucherRegisterRequest request) {
        Long voucherId = voucherService.createVoucher(VoucherType.valueOf(request.voucherType()), request.discountAmount());
        return ResponseEntity.ok(voucherId);
    }

    @DeleteMapping ("/delete/{id}")
    public void deleteVoucher(@PathVariable("id") long voucherId){
        voucherService.deleteVoucher(voucherId);
    }

}
