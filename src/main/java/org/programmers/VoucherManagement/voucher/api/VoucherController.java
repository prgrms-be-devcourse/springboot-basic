package org.programmers.VoucherManagement.voucher.api;

import org.programmers.VoucherManagement.voucher.application.VoucherService;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.springframework.stereotype.Component;

@Component
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }
    /**
     * 1. voucher 등록
     * 2. voucher 조회
     */
    public void createVoucher(CreateVoucherRequest createVoucherRequest){
        voucherService.saveVoucher(createVoucherRequest);
    }

    public GetVoucherListResponse getVoucherList(){ // class List<GetVoucherResponse>
        return voucherService.getVoucherList();
    }
}
