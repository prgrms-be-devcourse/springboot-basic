package org.programmers.VoucherManagement.voucher.api;

import org.programmers.VoucherManagement.voucher.application.VoucherService;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherReq;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListRes;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherRes;
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
    public GetVoucherRes createVoucher(CreateVoucherReq createVoucherReq){
        return voucherService.saveVoucher(createVoucherReq);
    }

    public GetVoucherListRes getVoucherList(){ // class List<GetVoucherRes>
        return voucherService.getVoucherList();
    }
}
