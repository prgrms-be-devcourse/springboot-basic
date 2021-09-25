package org.prgrms.kdt.voucher.controller;

import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // 다양한 인자로 바우처리스트 검색
    @GetMapping
    public ResponseEntity<List<Voucher>> getVoucherList(
            @RequestParam(name = "from", required = false, defaultValue = "1970-01-01") String from,
            @RequestParam(name = "to", required = false, defaultValue = "2999-12-31") String to,
            @RequestParam(name = "type", required = false, defaultValue = "") String type
    ){
        var voucherList = voucherService.getVoucherListByParams(from, to, type);
        return ResponseEntity.ok(voucherList);
    }

    // id로 바우처 검색
    @GetMapping("/{voucherId}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable("voucherId") UUID voucherId){
        var voucher = voucherService.getVoucherById(voucherId);
        return voucher.map(v -> ResponseEntity.ok(v))
                .orElse(ResponseEntity.notFound().build());
    }

    // 바우처 생성
    @PostMapping
    public void createVoucher(@RequestBody @Valid VoucherDto voucherDto){
        voucherService.createVoucher(voucherDto.getType(), voucherDto.getValue());
    }

    // 바우처 삭제
    @DeleteMapping("/{voucherId}")
    public void deleteVoucher(@PathVariable("voucherId") UUID voucherId){
        voucherService.deleteVoucher(voucherId);
    }



}
