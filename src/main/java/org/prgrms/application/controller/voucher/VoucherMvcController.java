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

    @GetMapping
    public ResponseEntity<List<VoucherDto>> getVouchers(){
        List<VoucherDto> vouchers = voucherService.getVouchers();
        return ResponseEntity.ok(vouchers);
    }

    @GetMapping("/voucher-type")
    public ResponseEntity<List<VoucherDto>> getVouchersByType
            (@RequestParam String voucherType){ //enum으로도 바로 받아진다, 하지만 강결합으로 이루어질 수 있으므로 생각해봐야 할 문제!
        List<VoucherDto> vouchers = voucherService.getVouchersByType(VoucherType.valueOf(voucherType));
        return ResponseEntity.ok(vouchers);
    }

    @PostMapping("/voucher")
    public ResponseEntity<Long> createVoucher(@RequestBody VoucherRegisterRequest request) {
        Long voucherId = voucherService.createVoucher(VoucherType.valueOf(request.voucherType()), request.discountAmount());
        return ResponseEntity.ok(voucherId);
    }

    @DeleteMapping ("/voucher/{id}")
    public void deleteVoucher(@PathVariable("id") long voucherId){
        voucherService.deleteVoucher(voucherId);
    }

}
