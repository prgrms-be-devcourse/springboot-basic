package com.voucher.vouchermanagement;

import com.voucher.vouchermanagement.manager.VoucherManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VoucherManagementApplication implements CommandLineRunner {

  private final VoucherManager voucherManager;

  public VoucherManagementApplication(VoucherManager voucherManager) {
    this.voucherManager = voucherManager;
  }

  public static void main(String[] args) {
    SpringApplication.run(VoucherManagementApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    voucherManager.run();
  }
}
