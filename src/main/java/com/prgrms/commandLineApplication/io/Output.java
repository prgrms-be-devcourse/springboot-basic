package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.customer.Customer;
import com.prgrms.commandLineApplication.voucher.Voucher;

import java.util.List;

public interface Output {

  void printMenu();

  void requestVoucherType();

  void requestDiscountAmount();

  void printAllVouchers(List<Voucher> vouchers);

  void printCreateVoucherSuccess(String voucherType, int discountAmount);

  void requestCustomerName();

  void requestCustomerEmail();

  void printAllCustomers(List<Customer> customers);

  void printCreateCustomerSuccess(String customerName, String email);

  void printErrorMessage(Exception e);

}
