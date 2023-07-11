package com.example.voucher.controller.response;

import java.util.List;
import com.example.voucher.constant.VoucherServiceType;
import com.example.voucher.domain.dto.VoucherDTO;

public class VoucherResponse {

    private final VoucherServiceType voucherServiceType;

    private VoucherDTO voucher;

    private List<VoucherDTO> vouchers;

    public VoucherServiceType getVoucherServiceType() {
        return voucherServiceType;
    }

    public VoucherDTO getVoucher() {
        return voucher;
    }

    public List<VoucherDTO> getVouchers() {
        return vouchers;
    }

    public VoucherResponse(VoucherServiceType voucherServiceType) {
        this.voucherServiceType = voucherServiceType;
    }

    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }

    public void setVoucherDTOS(List<VoucherDTO> voucherDTOS) {
        this.vouchers = voucherDTOS;
    }
}
