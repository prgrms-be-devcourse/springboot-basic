package com.weeklyMission.voucher.controller;

import com.weeklyMission.voucher.dto.VoucherRequest;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.service.VoucherService;
import java.util.List;
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
    public VoucherResponse save(@RequestBody VoucherRequest voucherRequest){
        return voucherService.save(voucherRequest);
    }

    @GetMapping
    public List<VoucherResponse> findAll(){
        return voucherService.findAll();
    }

    @GetMapping("/{memberId}")
    public VoucherResponse findById(@PathVariable("memberId") String memberId){
        return voucherService.findById(memberId);
    }

    @GetMapping("/search")
    public List<VoucherResponse> findByType(@RequestBody String type){
        return voucherService.findByType(type);
    }

    @DeleteMapping("/{memberId}")
    public void deleteById(@PathVariable("memberId") String memberId){
        voucherService.deleteById(memberId);
    }

}
