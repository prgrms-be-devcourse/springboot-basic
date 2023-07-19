package com.prgmrs.voucher.view.io;

import com.prgmrs.voucher.controller.console.wrapper.ResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ConsoleWriter {
    public <T> void write(T message) {
        System.out.println(message);
    }

    public void showManagementType() {
        for (ManagementType type : ManagementType.values()) {
            write(type.getValue());
        }
    }

    public void showResultMessage(ResponseDTO<?> response) {
        write(response.getData().toString());
    }
}
