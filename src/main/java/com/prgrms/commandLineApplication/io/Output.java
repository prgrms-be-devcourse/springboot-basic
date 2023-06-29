package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.voucher.Voucher;

import java.util.List;

public interface Output {

  void printMenu();

  void requestVoucherType();

  void requestDiscountAmount();

  void printError();

  void printAllVoucher(List<Voucher> vouchers);

  void printCreateSuccess();

}
