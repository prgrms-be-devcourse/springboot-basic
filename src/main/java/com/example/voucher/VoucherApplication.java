package com.example.voucher;

import com.example.voucher.domain.Voucher;
import com.example.voucher.domain.dto.VoucherDto;
import com.example.voucher.service.VoucherService;
import com.example.voucher.ui.Output;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication implements CommandLineRunner {
    private final VoucherService voucherService;
    private final CommandHandler commandHandler;

    public VoucherApplication(VoucherService voucherService, CommandHandler commandHandler) {
        this.voucherService = voucherService;
        this.commandHandler = commandHandler;
    }

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Output.printProgramInfo();

        Command command = commandHandler.handleCommand();
        switch (command) {
            case CREATE:
                VoucherDto voucherDto = commandHandler.handleCreateCommand();
                voucherService.createVoucher(voucherDto);
                break;
            case LIST:
                List<Voucher> vouchers = voucherService.getAllVouchers();
                commandHandler.handleListCommand(vouchers);
                break;
        }
    }
}
