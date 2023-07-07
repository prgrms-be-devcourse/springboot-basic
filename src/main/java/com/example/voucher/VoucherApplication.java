package com.example.voucher;

import com.example.voucher.config.VoucherConfig;
import com.example.voucher.domain.dto.VoucherDto;
import com.example.voucher.repository.VoucherRepository;
import com.example.voucher.ui.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(VoucherApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Output.printProgramInfo();

        VoucherConfig voucherConfig = new VoucherConfig();
        CommandHandler commandHandler = voucherConfig.commandHandler();
        VoucherRepository voucherRepository = voucherConfig.voucherRepository();

        Command command = commandHandler.handleCommand();
        switch (command) {
            case CREATE:
                VoucherDto voucherDto = commandHandler.handleCreateCommand();
                voucherRepository.insert(voucherDto.toVoucher());
                break;
            case LIST:
                commandHandler.handleListCommand(voucherRepository.findAll());
                break;
        }
    }
}
