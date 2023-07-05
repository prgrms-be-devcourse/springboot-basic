package com.example.springbootbasic.voucher;

import com.example.springbootbasic.io.Command;
import com.example.springbootbasic.io.ConsoleVoucherService;
import com.example.springbootbasic.io.Input;
import com.example.springbootbasic.io.Output;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Controller
public class CliVoucherApplication {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;

    public CliVoucherApplication(Input input, Output output, VoucherService voucherService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
    }

    @PostConstruct
    public void run() {
        while (true) {
            output.printCommandList();
            Optional<Command> inputCommand = input.getInputCommand("Type command: ");
            if (inputCommand.isEmpty()) {
                continue;
            }
            Command command = inputCommand.get();

            switch (command) {
                case EXIT -> {
                    ConsoleVoucherService.print("Terminate Program...");
                    return;
                }
                case CREATE -> {
                    Voucher voucher = input.inputVoucherInfo();
                    voucherService.createVoucher(voucher);
                }
                case LIST -> {
                    var voucherList = voucherService.getAllVouchers();
                    output.printAllVouchers(voucherList);
                }
                default -> {
                    ConsoleVoucherService.print("Invalid command, type again!\n");
                }
            }
        }
    }
}
