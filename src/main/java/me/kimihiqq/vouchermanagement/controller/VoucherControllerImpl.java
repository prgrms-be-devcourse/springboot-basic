package me.kimihiqq.vouchermanagement.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import me.kimihiqq.vouchermanagement.view.Console;

@Controller
@RequiredArgsConstructor
public class VoucherControllerImpl implements VoucherController {
    private final Console console;
    @Override
    public void run() {
        console.printLine("=== Voucher Program ===");
        console.printLine("Type **exit** to exit the program.");
        console.printLine("Type **create** to create a new voucher.");
        console.printLine("Type **list** to list all vouchers.");

        String input;
        while (!(input = console.readLine()).equalsIgnoreCase("exit")) {
            if (input.equalsIgnoreCase("create")) {
                // TODO: create voucher
                console.printLine("create voucher...");
            } else if (input.equalsIgnoreCase("list")) {
                // TODO: list vouchers
                console.printLine("list vouchers...");
            } else {
                console.printLine("잘못된 명령입니다. 다시 시도하세요.");
            }
        }
    }
}