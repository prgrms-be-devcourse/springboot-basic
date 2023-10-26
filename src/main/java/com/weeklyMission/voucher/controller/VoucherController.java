package com.weeklyMission.voucher.controller;

import com.weeklyMission.voucher.dto.VoucherRequest;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.service.VoucherService;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse create(VoucherRequest voucher){
        return voucherService.save(voucher);
    }

    public List<VoucherResponse> findAll(){
        return voucherService.findAll();
    }

    public VoucherResponse findById(UUID id){
        return voucherService.findById(id);
    }

    public void deleteById(UUID id){
        voucherService.deleteById(id);
    }
}
