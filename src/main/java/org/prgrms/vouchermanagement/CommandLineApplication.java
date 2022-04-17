package org.prgrms.vouchermanagement;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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

  public static final String ISSUE_COMMAND_CUSTOMER = "Input the Customer ID: ";
  public static final String ISSUE_COMMAND_VOUCHER = "Input the Voucher ID: ";

  private static final Scanner scanner = new Scanner(System.in);
  public static void main(String[] args) {
    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
    run(applicationContext);
  }

  static void run(ApplicationContext applicationContext) {
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
        case "issue":
          System.out.print(ISSUE_COMMAND_CUSTOMER);
          UUID customerId = UUID.fromString(scanner.nextLine());

          System.out.print(ISSUE_COMMAND_VOUCHER);
          UUID voucherId = UUID.fromString(scanner.nextLine());

          if(voucherService.issueVoucher(voucherId, customerId)) System.out.println("Issued!");
          break;
        default:
          System.out.println("Command does not exist");
      }
    }
  }
}
