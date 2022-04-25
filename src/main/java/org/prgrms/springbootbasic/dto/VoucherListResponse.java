package org.prgrms.springbootbasic.dto;

import java.util.List;

public class VoucherListResponse {

    private final List<VoucherDTO> voucherDtoList;

    public VoucherListResponse(
        List<VoucherDTO> voucherDtoList) {
        this.voucherDtoList = voucherDtoList;
    }

    public List<VoucherDTO> getVoucherDtoList() {
        return voucherDtoList;
    }
}
