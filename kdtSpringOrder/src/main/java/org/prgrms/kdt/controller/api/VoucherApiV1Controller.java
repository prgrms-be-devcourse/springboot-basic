package org.prgrms.kdt.controller.api;

import org.prgrms.kdt.controller.view.CustomerForm;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.dto.VoucherSaveRequestDto;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/vouchers")
public class VoucherApiV1Controller {

    private final VoucherService voucherService;

    public VoucherApiV1Controller(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping("/new")
    public ResponseEntity createVoucherByCustomerId(VoucherSaveRequestDto voucherSaveRequestDto){
        return voucherService.createVoucher(voucherSaveRequestDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{voucherId}/customers/{customerId}")
    public ResponseEntity deleteVoucherByCustomerId(@PathVariable UUID voucherId, @PathVariable UUID customerId) {
        voucherService.deleteVoucher(voucherId, customerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity findVoucher(@PathVariable UUID voucherId) {
        return ResponseEntity.ok().body(voucherService.getVoucher(voucherId));
    }

    @GetMapping("/type/{voucherType}")
    public ResponseEntity findVoucherByCustomerId(@PathVariable VoucherType voucherType) {
        return ResponseEntity.ok().body(voucherService.getVouchersByVoucherType(voucherType));
    }

    @GetMapping("/created-at")
    @ResponseBody
    public ResponseEntity<List<Voucher>> findVouchersByTerm(@RequestParam("start") String start, @RequestParam("end") String end){
        List<Voucher> voucher = voucherService.getVoucherByCreatedAt(start, end);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(voucher);
    }

}
