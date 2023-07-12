package org.devcourse.springbasic.global.io.output.voucher;

import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;

import java.util.List;

public interface VoucherOutput {

    void voucher(VoucherDto.ResponseDto voucher);
    void vouchers(List<VoucherDto.ResponseDto> vouchers);
    void menus();
    void voucherMenus();
}
