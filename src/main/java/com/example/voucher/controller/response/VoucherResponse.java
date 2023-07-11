package com.example.voucher.controller.response;

import java.util.List;
import com.example.voucher.domain.dto.VoucherDTO;

public class VoucherResponse {

    public enum Type{
        OBJECT,
        LIST,
        NONE;
    }

    private final Type type;

    private VoucherDTO voucher;

    private List<VoucherDTO> vouchers;

    public Type getType() {
        return type;
    }

    public VoucherDTO getVoucher() {
        return voucher;
    }

    public List<VoucherDTO> getVouchers() {
        return vouchers;
    }

    public VoucherResponse(VoucherResponse.Type type) {
        this.type = type;
    }

    public void setVoucher(VoucherDTO voucher) {
        this.voucher = voucher;
    }

    public void setVoucherDTOS(List<VoucherDTO> voucherDTOS) {
        this.vouchers = voucherDTOS;
    }
}
