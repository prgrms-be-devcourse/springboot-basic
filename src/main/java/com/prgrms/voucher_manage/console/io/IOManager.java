package com.prgrms.voucher_manage.console.io;

import com.prgrms.voucher_manage.console.ConsoleMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class IOManager {
    private final OutputUtil outputUtil;
    private final InputUtil inputUtil;

    public String getVoucherType() throws IOException {
        outputUtil.printVoucherSelect();
        return inputUtil.getStringInput();
    }

    public UUID getUUID(ConsoleMessage message) {
        outputUtil.printMessage(message.getMessage());
        return inputUtil.getUUIDInput();
    }

    public Long getLong(ConsoleMessage message) throws Exception {
        outputUtil.printMessage(message.getMessage());
        return inputUtil.getLongInput();
    }

    public String getString(ConsoleMessage message) throws IOException {
        outputUtil.printMessage(message.getMessage());
        return inputUtil.getStringInput();
    }
}
