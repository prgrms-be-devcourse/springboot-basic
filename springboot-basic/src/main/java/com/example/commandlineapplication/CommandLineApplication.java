package com.example.commandlineapplication;

import com.example.commandlineapplication.domain.voucher.controller.VoucherController;
import com.example.commandlineapplication.domain.voucher.repository.MemoryVoucherRepository;
import com.example.commandlineapplication.domain.voucher.service.VoucherFactory;
import com.example.commandlineapplication.domain.voucher.service.VoucherService;
import com.example.commandlineapplication.global.io.Input;
import com.example.commandlineapplication.global.io.Output;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommandLineApplication implements CommandLineRunner {

  private static final Logger log = LoggerFactory.getLogger(CommandLineApplication.class);

  public static void main(String[] args) {
    log.info("voucher가 실행되었습니다.");
    SpringApplication.run(CommandLineApplication.class, args);
  }

  @Override
  public void run(String... args) {
    VoucherController voucherController = new VoucherController(new Input(), new Output(),
        new VoucherFactory(), new VoucherService(new MemoryVoucherRepository()));
    voucherController.run();
  }
}
