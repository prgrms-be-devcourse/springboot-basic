package org.prgrms.VoucherManagement;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgrms.VoucherManagement.voucher.repository.VoucherRepository;
import org.prgrms.VoucherManagement.voucher.voucher.FixedAmountVoucher;
import org.prgrms.VoucherManagement.voucher.voucher.PercentDiscountVoucher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;


public class CommandLineApplication {
  public static final String AVAILABLE_COMMANDS = "=== Voucher Program ===\nType exit to exit the program.\nType create to create a new voucher.\nType list to list all vouchers.\n";
  public static final String AVAILABLE_VOUCHERS = "Which type do you want, FixedAmountVoucher(Fixed) or PercentDiscountVoucher(Percent)?";

  public static void main(String[] args) {
    var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    var voucherRepository = applicationContext.getBean(VoucherRepository.class);

    TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal terminal = textIO.getTextTerminal();
    terminal.print(AVAILABLE_COMMANDS);


    while (true) {
      String input = textIO.newStringInputReader()
        .read("Input: ");
      if(input.equals("exit")) {
        System.exit(0);
      }
      else if(input.equals("create")) {
        String voucherType;

        while (true) {
          voucherType = textIO.newStringInputReader()
            .read(AVAILABLE_VOUCHERS);

          if(voucherType.equals("Fixed") || voucherType.equals("FixedAmountVoucher")) {
            voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L)); // fix later
            break;
          }
          else if(voucherType.equals("Percent") || voucherType.equals("PercentDiscountVoucher")) {
            voucherRepository.insert(new PercentDiscountVoucher(UUID.randomUUID(), 10L)); // fix later
            break;
          } else {
            terminal.printf("%s is a non-existent voucher type.\n", voucherType);
          }
        }
      }
      else if(input.equals("list")) {
        terminal.println(voucherRepository.findAll());
      }
      else {
        terminal.printf("%s is a non-existent command.\n", input);
      }
    }
  }
}
