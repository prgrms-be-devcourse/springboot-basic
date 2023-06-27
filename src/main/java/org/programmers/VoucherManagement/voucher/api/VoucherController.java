package org.programmers.VoucherManagement.voucher.api;

import org.programmers.VoucherManagement.voucher.application.VoucherService;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
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
    public Voucher createVoucher(CreateVoucherRequest createVoucherRequest){
        return voucherService.saveVoucher(createVoucherRequest);
    }

    public List<Voucher> getVoucherList(){
        return voucherService.getVoucherList();
    }
}
