package org.prgrms.kdt.devcourse.io;

import org.prgrms.kdt.devcourse.customer.Customer;
import org.prgrms.kdt.devcourse.voucher.Voucher;

import java.util.List;

public interface Output {
   void inputError(String errorInput);
   void printOut(String message);
   void printVoucherList(List<Voucher> vouchers);
   void printCustomerList(List<Customer> customers);
}
