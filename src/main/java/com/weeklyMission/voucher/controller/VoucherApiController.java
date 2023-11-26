package com.weeklyMission.voucher.controller;

import com.weeklyMission.voucher.dto.VoucherRequest;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.service.VoucherService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    public ResponseEntity<VoucherResponse> createVoucher(@RequestBody VoucherRequest voucherRequest){
        VoucherResponse save = voucherService.save(voucherRequest);
        return ResponseEntity.ok(save);
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponse>> getAllVoucher(){
        List<VoucherResponse> all = voucherService.findAll();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<VoucherResponse> getVoucherById(@PathVariable("memberId") String memberId){
        VoucherResponse voucherResponse = voucherService.findById(memberId);
        return ResponseEntity.ok(voucherResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<VoucherResponse>> getVoucherByType(@RequestBody String type){
        List<VoucherResponse> voucherResponses = voucherService.findByType(type);
        return ResponseEntity.ok(voucherResponses);
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Boolean> deleteVoucherById(@PathVariable("memberId") String memberId){
        voucherService.deleteById(memberId);
        return ResponseEntity.ok(true);
    }

}
