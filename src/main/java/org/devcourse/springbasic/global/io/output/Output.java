package org.devcourse.springbasic.global.io.output;

import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;

import java.util.List;

public interface Output {

    void voucher(VoucherDto.ResponseDto voucher);
    void vouchers(List<VoucherDto.ResponseDto> vouchers);
    void menus();
    void voucherMenus();
}
