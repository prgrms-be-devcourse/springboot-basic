package org.prgrms.vouchermanagement.console;

import org.prgrms.vouchermanagement.customer.Customer;
import org.prgrms.vouchermanagement.voucher.voucher.Voucher;

import java.util.List;

public interface Output {

  void printCommandList();

  void printNotExistingCommand();

  void printListType();

  void printVoucherList(List<Voucher> list);

  void printCustomerList(List<Customer> list);
}