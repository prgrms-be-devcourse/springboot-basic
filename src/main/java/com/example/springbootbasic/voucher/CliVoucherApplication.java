package com.example.springbootbasic.voucher;

import com.example.springbootbasic.io.Command;
import com.example.springbootbasic.io.ConsoleVoucherService;
import com.example.springbootbasic.io.Input;
import com.example.springbootbasic.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Component
public class CliVoucherApplication {
    private static final Logger logger = LoggerFactory.getLogger(CliVoucherApplication.class);

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
                    logger.info("Terminate Program...");
                    return;
                }
                case CREATE -> {
                    Voucher voucher = input.inputVoucherInfo();
                    voucherService.createVoucher(voucher);
                    logger.info("바우처 생성 성공!, 바우처 정보: {}", voucher.getClass().getSimpleName());
                }
                case LIST -> {
                    var voucherList = voucherService.getAllVouchers();
                    output.printAllVouchers(voucherList);
                    logger.info("바우처 목록 출력 성공!");
                }
                default -> ConsoleVoucherService.print("Invalid command, type again!\n");
            }
        }
    }
}
