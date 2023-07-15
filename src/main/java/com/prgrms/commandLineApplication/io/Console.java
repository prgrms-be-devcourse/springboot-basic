package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.customer.Customer;
import com.prgrms.commandLineApplication.customer.dto.CustomerResponseDto;
import com.prgrms.commandLineApplication.voucher.Voucher;
import com.prgrms.commandLineApplication.voucher.discount.DiscountType;
import com.prgrms.commandLineApplication.voucher.dto.VoucherCreateDto;
import com.prgrms.commandLineApplication.voucher.dto.VoucherResponseDto;
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
          Type create_Customer to create a new customer.
          Type customer_List to list all customers.
          Type create_Voucher to create a new voucher.
          Type voucher_List to list all vouchers.
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
  public String readCustomer() {
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
  public void printAllVouchers(List<VoucherResponseDto> vouchers) {
    for (VoucherResponseDto voucher : vouchers) {
      DiscountType discountType = voucher.discountType();
      System.out.println(String.format("- VOUCHER_ID : %s \n- %s VOUCHER : %s%s",
              voucher.voucherId(), discountType, voucher.discountAmount(), discountType.getUnit(discountType)));
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
  public void printAllCustomers(List<CustomerResponseDto> customers) {
    for (CustomerResponseDto customer : customers) {
      System.out.println(String.format("- CUSTOMER_ID : %s \n- NAME : %s, EMAIL : %s",
              customer.customerId(), customer.customerName(), customer.email()));
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
