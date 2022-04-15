package com.voucher.vouchermanagement.manager;

import static com.voucher.vouchermanagement.manager.exception.ConsumerExceptionWrapper.throwingConsumerWrapper;
import com.voucher.vouchermanagement.manager.io.VoucherManagerInput;
import com.voucher.vouchermanagement.manager.io.VoucherManagerOutput;
import com.voucher.vouchermanagement.service.AddVoucherService;
import com.voucher.vouchermanagement.service.BlacklistService;
import com.voucher.vouchermanagement.service.FindVoucherService;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherManager {

  private final AddVoucherService addVoucherService;
  private final FindVoucherService findVoucherService;
  private final BlacklistService blackListService;
  private final VoucherManagerInput input;
  private final VoucherManagerOutput output;
  private static final Logger logger = LoggerFactory.getLogger(VoucherManager.class);

  public VoucherManager(AddVoucherService addVoucherService, FindVoucherService findVoucherService, BlacklistService blackListService,
      VoucherManagerInput input, VoucherManagerOutput output) {
    this.addVoucherService = addVoucherService;
    this.findVoucherService = findVoucherService;
    this.blackListService = blackListService;
    this.input = input;
    this.output = output;
  }

  public void run() throws IOException {
    while (true) {
      try {
        output.printMenu();
        String command = input.input("input command : ");
        if (!doCommand(command)) {
          break;
        }
      } catch (IllegalArgumentException e) {
        logger.error("Catch IllegalArgumentException {}", e.getMessage());
        output.println(e.getMessage());
      } catch (IOException e) {
        logger.error("Catch IO Exception");
        logger.error(e.getMessage());
        output.println("입출력 오류 발생");
      } catch (Exception e) {
        logger.error("예기치 못한 오류 발생.");
        logger.error(e.getMessage());
        output.println("Error");
      }
    }
  }

  private boolean doCommand(String command) throws IOException {
    if (command.equals("create")) {
      return createCommand();
    } else if (command.equals("list")) {
      return listCommand();
    } else if (command.equals("blacklist")) {
      return blacklistCommand();
    } else if (command.equals("exit")) {
      return false;
    } else {
      logger.error("doCommand() : wrong input {}", command);
      throw new IllegalArgumentException("잘못된 명령어 입력입니다.");
    }
  }

  private boolean createVoucher() throws IOException {
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

    return true;
  }

  private boolean printVouchers() throws IOException {
    output.println("=== [Voucher List] ===");
    List<Voucher> vouchers = voucherService.findAll();

    for (Voucher voucher : vouchers) {
      output.println(voucher.toString());
    }

    return true;
  }

  private boolean printBlacklist() throws IOException {
    output.println("===  [Blacklist]  ===");
    List<Customer> blacklist = blackListService.findAll();

    for (Customer user : blacklist) {
      output.println(user.toString());
    }

    return true;
  }

}
