package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.customer.dto.CustomerResponseDto;
import com.prgrms.commandLineApplication.voucher.dto.VoucherResponseDto;

import java.util.List;

public interface Output {

  void printMenu();

  void requestVoucherType();

  void requestDiscountAmount();

  void printAllVouchers(List<VoucherResponseDto> vouchers);

  void printCreateVoucherSuccess(String voucherType, int discountAmount);

  void requestCustomerName();

  void requestCustomerEmail();

  void printAllCustomers(List<CustomerResponseDto> customers);

  void printCreateCustomerSuccess(String customerName, String email);

  void printErrorMessage(Exception e);

}
