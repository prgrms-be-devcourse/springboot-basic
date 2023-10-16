package com.weeklyMission.controller;

import com.weeklyMission.domain.Voucher;
import com.weeklyMission.dto.VoucherResponse;
import com.weeklyMission.service.VoucherService;
import java.util.List;
import org.springframework.stereotype.Controller;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse create(Voucher voucher){
        return new VoucherResponse(
            voucherService.createVoucher(voucher));
    }

    public List<VoucherResponse> getVoucherList(){
        List<Voucher> voucherList = voucherService.getVoucherList();
        return voucherList.stream()
            .map(VoucherResponse::new)
            .toList();
    }
}
