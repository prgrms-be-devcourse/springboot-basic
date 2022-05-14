package org.prgrms.vouchermanagement.console;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleOutput implements Output {
  public static final String AVAILABLE_COMMANDS = "=== Voucher Program ===" +
    System.lineSeparator() +
    "Type exit to exit the program." +
    System.lineSeparator() +
    "Type create to create a new voucher." +
    System.lineSeparator() +
    "Type \"list voucher\" to list all vouchers." +
    System.lineSeparator() +
    "Type \"list customer\" to list all vouchers." +
    System.lineSeparator() +
    "Type issue to issue voucher to customer" +
    System.lineSeparator();

  private static final String NOT_EXISTING_COMMAND = "Command does not exist";

  private static final String LIST_TYPE = "Which do you want customer or voucher? (customer OR voucher): ";

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
  public void printVoucherList(List<Voucher> list) {
    for(Object voucher: list) {
      System.out.println(voucher);
    }
  }

  @Override
  public void printCustomerList(List<Customer> list) {
    for(Customer customer: list) {
      System.out.println(customer);
    }
  }
}