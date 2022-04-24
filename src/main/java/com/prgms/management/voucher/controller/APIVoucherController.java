package com.prgms.management.voucher.controller;

import com.prgms.management.common.dto.Response;
import com.prgms.management.voucher.dto.VoucherRequest;
import com.prgms.management.voucher.dto.VoucherResponse;
import com.prgms.management.voucher.model.Voucher;
import com.prgms.management.voucher.model.VoucherType;
import com.prgms.management.voucher.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/vouchers")
public class APIVoucherController {
    private final VoucherService voucherService;
    
    public APIVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    
    @GetMapping
    public ResponseEntity<Response> voucherList(@RequestParam HashMap<String, String> param) {
        VoucherType type = null;
        Timestamp start = null;
        Timestamp end = null;
        
        try {
            type = VoucherType.valueOf(param.get("type").toUpperCase());
        } catch (NullPointerException ignored) {}
        
        try {
            start = Timestamp.valueOf(param.get("start"));
            end = Timestamp.valueOf(param.get("end"));
        } catch (NullPointerException | IllegalArgumentException ignored) {}
        
        List<Voucher> voucherList = voucherService.findVouchers(type, start, end);
        List<VoucherResponse> resultList = voucherList.stream().map(VoucherResponse::of).toList();
        Response response = new Response(HttpStatus.OK, "바우처 조회 성공", resultList);
        
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<Response> voucherAdd(@RequestBody VoucherRequest request) {
        Voucher voucher = voucherService.addVoucher(request.toVoucher());
        Response response = new Response(HttpStatus.OK, "바우처 등록 성공", VoucherResponse.of(voucher));
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Response> voucherDetail(@PathVariable("id") UUID id) {
        Voucher voucher = voucherService.findVoucherById(id);
        Response response = new Response(HttpStatus.OK, "바우처 조회 성공", VoucherResponse.of(voucher));
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Response> voucherRemove(@PathVariable("id") UUID id) {
        voucherService.removeVoucherById(id);
        Response response = new Response(HttpStatus.OK, "바우처 삭제 성공", null);
        return ResponseEntity.ok(response);
    }
}
