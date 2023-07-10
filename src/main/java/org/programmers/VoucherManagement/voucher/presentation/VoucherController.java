package org.programmers.VoucherManagement.voucher.presentation;

import org.programmers.VoucherManagement.voucher.application.VoucherService;
import org.programmers.VoucherManagement.voucher.dto.CreateVoucherRequest;
import org.programmers.VoucherManagement.voucher.dto.GetVoucherListResponse;
import org.programmers.VoucherManagement.voucher.dto.UpdateVoucherRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

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
    public void createVoucher(CreateVoucherRequest createVoucherRequest) {
        voucherService.saveVoucher(createVoucherRequest);
    }

    public GetVoucherListResponse getVoucherList() {
        return voucherService.getVoucherList();
    }

    public void deleteVoucher(UUID voucherId) {
        voucherService.deleteVoucher(voucherId);
    }

    public void updateVoucher(UUID voucherId, UpdateVoucherRequest updateVoucherRequest) {
        voucherService.updateVoucher(voucherId, updateVoucherRequest);
    }
}
