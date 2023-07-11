package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.customer.Customer;
import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.discount.DiscountType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Console implements Input, Output {

  private static final Logger logger = LoggerFactory.getLogger(Console.class);
  private static final Scanner scanner = new Scanner(System.in);

  public static final String ENTER_CREATE_TYPE = "Enter the type you want to create";
  public static final String ENTER_CREATE_VALUE = "Enter the value you want to create";

  public static final String ENTER_CREATE_CUSTOMER_NAME = "Enter the name of the account you want to create";
  public static final String ENTER_CREATE_CUSTOMER_EMAIL = "Enter the email for the account you want to create";

  public static final String CREATE_SUCCESS = "Successfully created";
  public static final String PRINT_MENU = """
          === Voucher Program ===
          Type exit to exit the program.
          Type createCustomer to create a new customer.
          Type customerList to list all customers.
          Type createVoucher to create a new voucher.
          Type voucherList to list all vouchers.
          """;

  private String userInput() {
    return scanner.nextLine();
  }

  @Override
  public String readMenu() {
    return userInput();
  }

  @Override
  public String readVoucherType() {
    return userInput();
  }

  @Override
  public int readVoucherAmount() {
    return Integer.parseInt(userInput());
  }

  @Override
  public String readCustomerName() {
    return userInput();
  }

  @Override
  public String readCustomerEmail() {
    return userInput();
  }

  @Override
  public void requestVoucherType() {
    System.out.println("FIXED" + " | " + "PERCENT\n" + ENTER_CREATE_TYPE);
  }

  @Override
  public void requestDiscountAmount() {
    System.out.println(ENTER_CREATE_VALUE);
  }

  @Override
  public void printMenu() {
    System.out.println(PRINT_MENU);
  }

  @Override
  public void printAllVouchers(List<Voucher> vouchers) {
    for (Voucher voucher : vouchers) {
      DiscountType discountType = voucher.supplyDiscountType();
      System.out.println(String.format("- VOUCHER_ID : %s", voucher.getVoucherId()));
      System.out.println(String.format("- %s VOUCHER : %s%s", discountType, voucher.supplyDiscountAmount(), DiscountType.getUnit(discountType)));
    }
  }

  @Override
  public void requestCustomerName() {
    System.out.println(ENTER_CREATE_CUSTOMER_NAME);
  }

  @Override
  public void requestCustomerEmail() {
    System.out.println(ENTER_CREATE_CUSTOMER_EMAIL);
  }

  @Override
  public void printAllCustomers(List<Customer> customers) {
    for (Customer customer : customers) {
      System.out.println(String.format("- CUSTOMER_ID : %s", customer.getCustomerId()));
      System.out.println(String.format("- NAME : %s, EMAIL : %s", customer.getCustomerName(), customer.getEmail()));
    }
  }

  @Override
  public void printCreateCustomerSuccess(String customerName, String email) {
    logger.info("{} : CUSTOMER => {} {} ", CREATE_SUCCESS, customerName, email);
  }

  @Override
  public void printCreateVoucherSuccess(String voucherType, int discountAmount) {
    logger.info("{} : VOUCHER => {} {} ", CREATE_SUCCESS, voucherType.toUpperCase(), discountAmount);
  }

  @Override
  public void printErrorMessage(Exception e) {
    System.out.println(e.getMessage());
  }

}
