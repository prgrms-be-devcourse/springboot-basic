package org.prgrms.vouchermanagement.console;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutput implements Output {
  public static final String AVAILABLE_COMMANDS = "=== Voucher Program ===\n" +
    "Type exit to exit the program.\n" +
    "Type create to create a new voucher.\n" +
    "Type \"list voucher\" to list all vouchers.\n" +
    "Type \"list customer\" to list all vouchers.\n" +
    "Type issue to issue voucher to customer\n";

  private static final String NOT_EXISTING_COMMAND = "Command does not exist";

  private static final String LIST_TYPE = "Which do you want customer or voucher? (customer OR voucher): ";
  public static final String AVAILABLE_VOUCHERS = "Which type do you want?\n\t" +
    "1. FixedAmountVoucher(Fixed)\n\t" +
    "2. PercentDiscountVoucher(Percent)";

  public static final String ISSUE_COMMAND_CUSTOMER = "Input the Customer ID: ";

  public static final String ISSUE_COMMAND_VOUCHER = "Input the Voucher ID: ";


  @Override
  public void printCommandList() {
    System.out.println(AVAILABLE_COMMANDS);
  }

  @Override
  public void printNotExistingCommand() {
    System.out.println(NOT_EXISTING_COMMAND);
  }

  @Override
  public void printListType() {
    System.out.print(LIST_TYPE);
  }

  @Override
  public void printAvailableVoucherType() {
    System.out.println(AVAILABLE_VOUCHERS);
  }

  @Override
  public void printIssueVoucher() {
    System.out.print(ISSUE_COMMAND_CUSTOMER);
  }

  @Override
  public void printIssueCustomer() {
    System.out.println(ISSUE_COMMAND_VOUCHER);
  }

  @Override
  public void printList(List list) {
    for(Object element: list) {
      System.out.println(element);
    }
  }
}
