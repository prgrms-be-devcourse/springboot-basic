package com.devcourse.springbootbasic.application.util.template;

import com.devcourse.springbootbasic.application.domain.Voucher;
import com.devcourse.springbootbasic.application.dto.VoucherDto;
import com.devcourse.springbootbasic.application.service.VoucherService;
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
