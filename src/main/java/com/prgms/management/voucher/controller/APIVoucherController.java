package com.prgms.management.voucher.controller;

import com.prgms.management.common.dto.Response;
import com.prgms.management.common.exception.WrongRequestParamException;
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
        VoucherType type;
        Timestamp start, end;
        
        try {
            type = VoucherType.valueOf(param.get("type").toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new WrongRequestParamException("type은 fixed와 percent만 지원합니다.");
        } catch (NullPointerException e) {
            type = null;
        }
        
        try {
            start = Timestamp.valueOf(param.get("start"));
            end = Timestamp.valueOf(param.get("end"));
        } catch (IllegalArgumentException e) {
            throw new WrongRequestParamException("start와 end의 포맷은 'yyyy-mm-dd hh:mm:ss[.fffffffff]' 형식이어야 합니다.");
        } catch (NullPointerException e) {
            start = null;
            end = null;
        }
        
        List<Voucher> voucherList = voucherService.findVouchers(type, start, end);
        List<VoucherResponse> resultList = voucherList.stream().map(VoucherResponse::of).toList();
        Response response = new Response(HttpStatus.OK.value(), "바우처 조회 성공", resultList);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping
    public ResponseEntity<Response> voucherAdd(@RequestBody VoucherRequest request) {
        Voucher voucher = voucherService.addVoucher(request.toVoucher());
        Response response = new Response(HttpStatus.OK.value(), "바우처 등록 성공", VoucherResponse.of(voucher));
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Response> voucherDetail(@PathVariable("id") UUID id) {
        Voucher voucher = voucherService.findVoucherById(id);
        Response response = new Response(HttpStatus.OK.value(), "바우처 조회 성공", VoucherResponse.of(voucher));
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<Response> voucherRemove(@PathVariable("id") UUID id) {
        voucherService.removeVoucherById(id);
        Response response = new Response(HttpStatus.OK.value(), "바우처 삭제 성공", null);
        return ResponseEntity.ok(response);
    }
    
    @ExceptionHandler(WrongRequestParamException.class)
    public ResponseEntity<Response> controlWrongRequestParamException(WrongRequestParamException e) {
        Response response = new Response(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.badRequest().body(response);
    }
}
