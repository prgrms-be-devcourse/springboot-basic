package com.weeklyMission.voucher.controller;

import com.weeklyMission.voucher.dto.VoucherRequest;
import com.weeklyMission.voucher.dto.VoucherResponse;
import com.weeklyMission.voucher.service.VoucherService;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse create(VoucherRequest voucher){
        return voucherService.createVoucher(voucher);
    }

    public List<VoucherResponse> getVoucherList(){
        return voucherService.getVoucherList();
    }
}
