package org.programmers.VoucherManagement.voucher.presentation;

import org.programmers.VoucherManagement.voucher.application.VoucherService;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherCreateRequest;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherGetResponses;
import org.programmers.VoucherManagement.voucher.application.dto.VoucherUpdateRequest;
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
    public void createVoucher(VoucherCreateRequest voucherCreateRequest) {
        voucherService.saveVoucher(voucherCreateRequest);
    }

    public VoucherGetResponses getVoucherList() {
        return voucherService.getVoucherList();
    }

    public void deleteVoucher(String voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

    public void updateVoucher(String voucherId, VoucherUpdateRequest voucherUpdateRequest) {
        voucherService.updateVoucher(voucherId, voucherUpdateRequest);
    }
}
