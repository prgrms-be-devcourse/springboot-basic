package org.prgrms;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class VoucherCommandLineRunner implements CommandLineRunner {

  private final VoucherProcessManager voucherProcessManager;

  public VoucherCommandLineRunner(VoucherProcessManager voucherProcessManager) {
    this.voucherProcessManager = voucherProcessManager;
  }

  @Override
  public void run(String... args) {

    while (true) {
      try {
        voucherProcessManager.execute();
      } catch (RuntimeException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
