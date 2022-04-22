package org.prgrms.springbootbasic.dto;

import java.util.List;

public class FindAllVoucherResponse {

    private final List<VoucherDTO> voucherDtoList;

    public FindAllVoucherResponse(
        List<VoucherDTO> voucherDtoList) {
        this.voucherDtoList = voucherDtoList;
    }

    public List<VoucherDTO> getVoucherDtoList() {
        return voucherDtoList;
    }
}
