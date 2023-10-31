package org.prgms.kdtspringweek1.controller;

import org.prgms.kdtspringweek1.console.ConsoleInput;
import org.prgms.kdtspringweek1.voucher.service.dto.CreateVoucherRequestDto;
import org.prgms.kdtspringweek1.controller.dto.SelectFunctionTypeDto;
import org.prgms.kdtspringweek1.voucher.service.dto.SelectVoucherTypeDto;
import org.prgms.kdtspringweek1.voucher.service.dto.UpdateVoucherRequestDto;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    public UUID getId() {
        return UUID.fromString(consoleInput.getInput());
    }

    public String getCustomerName() {
        return consoleInput.getInput();
    }

    public boolean getIsBlackCustomer() {
        return Boolean.parseBoolean(consoleInput.getInput());
    }

    public UpdateVoucherRequestDto getUpdateVoucherRequestDto(UUID voucherId) {
        return new UpdateVoucherRequestDto(voucherId, Long.parseLong(consoleInput.getInput()));
    }
}
