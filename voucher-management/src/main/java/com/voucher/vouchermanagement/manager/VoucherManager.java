package com.voucher.vouchermanagement.manager;

import com.voucher.vouchermanagement.manager.command.CommandType;
import com.voucher.vouchermanagement.manager.io.VoucherManagerInput;
import com.voucher.vouchermanagement.manager.io.VoucherManagerOutput;
import com.voucher.vouchermanagement.model.customer.Customer;
import com.voucher.vouchermanagement.model.voucher.Voucher;
import com.voucher.vouchermanagement.model.voucher.VoucherType;
import com.voucher.vouchermanagement.service.BlacklistService;
import com.voucher.vouchermanagement.service.VoucherService;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherManager {

  private final VoucherService voucherService;
  private final BlacklistService blackListService;
  private final VoucherManagerInput input;
  private final VoucherManagerOutput output;
  private static final Logger logger = LoggerFactory.getLogger(VoucherManager.class);

  public VoucherManager(VoucherService voucherService, BlacklistService blackListService,
      VoucherManagerInput input, VoucherManagerOutput output) {
    this.voucherService = voucherService;
    this.blackListService = blackListService;
    this.input = input;
    this.output = output;
  }

  public void run() throws IOException {
    while (true) {
      try {
        output.printMenu();
        CommandType commandType = CommandType.getCommandTypeByName(input.input("input command : "));
        switch (commandType) {
          case CREATE: {
            createVoucher();
            break;
          }
          case LIST: {
            printVouchers();
            break;
          }
          case BLACKLIST: {
            printBlacklist();
            break;
          }
          case EXIT: {
            return;
          }
        }
      } catch (IllegalArgumentException e) {
        logger.error(e.getMessage());
        output.println(e.getMessage());
      }
    }
  }

  private void createVoucher() throws IOException {
    try {
      output.println("=== [Create Voucher] ===");
      output.printVoucherType();
      int voucherTypeNumberInput = Integer.parseInt(input.input("Input voucher type : "));
      VoucherType voucherType = VoucherType.getVoucherTypeByNumber(voucherTypeNumberInput);
      long voucherValue = Long.parseLong(input.input("Input voucher value"));

      voucherService.insertVoucher(voucherType, voucherValue);
      output.println("Voucher Creation Completed.");
    } catch (IllegalArgumentException e) {
      logger.error(e.getMessage());
    }
  }

  private void printVouchers() throws IOException {
    output.println("=== [Voucher List] ===");
    List<Voucher> vouchers = voucherService.findAll();

    for (Voucher voucher : vouchers) {
      output.println(voucher.toString());
    }
  }

  private void printBlacklist() throws IOException {
    output.println("===  [Blacklist]  ===");
    List<Customer> blacklist = blackListService.findAll();

    for (Customer user : blacklist) {
      output.println(user.toString());
    }
  }

}
