package com.prgrms.springbootbasic;

import com.prgrms.springbootbasic.repository.MemoryVoucherRepository;
import com.prgrms.springbootbasic.repository.VoucherRepository;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class VoucherApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoucherApplication.class, args);
    }
    @Bean
    public CommandLineRunner consoleRunner() {
        return args -> {
            TextIO textIO = TextIoFactory.getTextIO();
            try {
                TextTerminal<?> terminal = textIO.getTextTerminal();
                VoucherRepository voucherRepository = new MemoryVoucherRepository();
                Console<TextTerminal<?>> console = new Console<>(textIO, terminal, voucherRepository);
                console.run();
            } finally {
                textIO.dispose();
            }
        };
    }
}
