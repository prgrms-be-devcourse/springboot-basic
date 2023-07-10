package com.prgmrs.voucher.view.writer;

import org.springframework.stereotype.Component;

@Component
public class ConsoleMainWriter {
    public void write(String message) {
        System.out.println(message);
    }

    public void showManagementType() {
        write("=== Voucher Program ===");
        write("Type 'create' to create a voucher or user.");
        write("Type 'list' to list vouchers.");
        write("Type 'assign' to manage voucher assignment");
        write("Type 'exit' to exit the program.");
    }
}
