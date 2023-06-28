package com.devcourse.springbootbasic.application.global.template;

import com.devcourse.springbootbasic.application.domain.voucher.data.Voucher;
import com.devcourse.springbootbasic.application.domain.voucher.dto.VoucherDto;
import com.devcourse.springbootbasic.application.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class CreateMenuTemplate {

    private final VoucherService voucherService;

    public CreateMenuTemplate(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher createTask(VoucherDto voucherDto) {
        return voucherService.createVoucher(voucherDto);
    }

}
