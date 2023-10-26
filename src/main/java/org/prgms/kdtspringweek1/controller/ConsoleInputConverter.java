package org.prgms.kdtspringweek1.controller;

import org.prgms.kdtspringweek1.console.ConsoleInput;
import org.prgms.kdtspringweek1.controller.dto.CreateVoucherRequestDto;
import org.prgms.kdtspringweek1.controller.dto.SelectFunctionTypeDto;
import org.prgms.kdtspringweek1.controller.dto.SelectVoucherTypeDto;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.springframework.stereotype.Component;

@Component
public class ConsoleInputConverter {

    private final ConsoleInput consoleInput;

    public ConsoleInputConverter(ConsoleInput consoleInput) {
        this.consoleInput = consoleInput;
    }

    public SelectFunctionTypeDto getFunctionType() {
        return SelectFunctionTypeDto.getValueByName(consoleInput.getInput());
    }

    public VoucherType getVoucherType() {
        return SelectVoucherTypeDto.getVoucherTypeByNum(Long.parseLong(consoleInput.getInput()));
    }

    public CreateVoucherRequestDto getCreateVoucherRequestDto() {
        return new CreateVoucherRequestDto(Long.parseLong(consoleInput.getInput()));
    }
}
