package org.programmers.VoucherManagement.voucher.api;

import org.programmers.VoucherManagement.voucher.application.VoucherService;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherResponse;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    /**
     * 1. voucher 등록
     * 2. voucher 조회
     */
    public GetVoucherResponse createVoucher(CreateVoucherRequest createVoucherRequest){
        return voucherService.saveVoucher(createVoucherRequest);
    }

    public List<GetVoucherResponse> getVoucherList(){
        return voucherService.getVoucherList();
    }
}
