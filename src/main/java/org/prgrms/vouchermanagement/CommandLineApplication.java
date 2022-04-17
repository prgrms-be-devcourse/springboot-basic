package org.prgrms.vouchermanagement;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.customer.repository.CustomerRepository;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherFactory;
import org.prgrms.vouchermanagement.voucher.voucher.VoucherType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.UUID;


public class CommandLineApplication {
  public static final String AVAILABLE_COMMANDS = "=== Voucher Program ===\n" +
    "Type exit to exit the program.\n" +
    "Type create to create a new voucher.\n" +
    "Type \"list voucher\" to list all vouchers.\n" +
    "Type \"list customer\" to list all vouchers.\n" +
    "Type issue to issue voucher to customer\n";

  public static final String AVAILABLE_VOUCHERS = "Which type do you want?\n\t" +
    "1. FixedAmountVoucher(Fixed)\n\t" +
    "2. PercentDiscountVoucher(Percent)";
  private static final Scanner scanner = new Scanner(System.in);
  public static void main(String[] args) {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

    // 1. 사용 가능한 command를 출력
    System.out.println(AVAILABLE_COMMANDS);

    VoucherService voucherService = applicationContext.getBean(VoucherService.class);
    while(true) {
      System.out.print("Input command: ");
      String command = scanner.nextLine().trim().toLowerCase();

      switch (command) {
        case "exit":
          System.exit(0);
          break;
        case "create":
          System.out.println(AVAILABLE_VOUCHERS);
          String voucherType;
          if((voucherType = scanner.nextLine()).matches("\\d+")) {
          }
          else {
            System.out.println("You can only enter numbers\n");
          }
          break;
        case "list voucher":
          for(Voucher voucher:voucherService.getVoucherList()) {
            System.out.println(voucher);
          }
          break;
        case "list customer":
          for(Customer customer:voucherService.getCustomerList()) {
            System.out.println(customer);
          }
          break;
        default:
          System.out.println("Command does not exist");
      }
    }


    /*while (true) {
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
    }*/
  }
}
