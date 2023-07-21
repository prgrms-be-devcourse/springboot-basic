package com.prgrms.voucher.controller;

import com.prgrms.common.KeyGenerator;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.service.VoucherResponse;
import com.prgrms.voucher.service.VoucherService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2")
public class VoucherV2Controller {


    private final VoucherService voucherService;
    private final KeyGenerator keyGenerator;

    public VoucherV2Controller(VoucherService voucherService, KeyGenerator keyGenerator) {
        this.voucherService = voucherService;
        this.keyGenerator = keyGenerator;
    }

    // 조건별 조회도 넣자

    @GetMapping("/vouchers")
    public ResponseEntity<List<VoucherResponse>> getVouchers(
            @RequestParam(value = "voucherType", required = false) VoucherType voucherType
            , @RequestParam(value = "createdAt", required = false) LocalDateTime startCreatedAt) {

        return new ResponseEntity<>(voucherService.getAllVoucherList(voucherType, startCreatedAt), HttpStatus.OK);
    }

    // 바우처 삭제

    @DeleteMapping("/voucher/{voucherId}")
    public int deleteVoucher(@PathVariable("voucherId") int voucherId) {

        return voucherService.deleteByVoucherId(voucherId);
    }

    // 바우처 아이디로 검색

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<VoucherResponse> detailVoucher(@PathVariable("voucherId") int voucherId) {

        return new ResponseEntity<>(voucherService.detailVoucher(voucherId), HttpStatus.OK);
    }

    // 바우처 추가
    @PostMapping("/voucher/new")
    public String createVoucher(@RequestParam("voucherType") VoucherType voucherType,
            @RequestParam("discountAmount") double discountAmount) {
        int id = keyGenerator.make();
        voucherService.createVoucher(id, voucherType, discountAmount, LocalDateTime.now());

        return "redirect:/v1/vouchers";
    }

}

