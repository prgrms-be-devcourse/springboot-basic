package org.prgrms.springbootbasic.console;

import org.prgrms.springbootbasic.voucher.voucher.Voucher;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Output {

  void printVoucherList(Map<UUID, Voucher> vouchers);

  void printAvailableCommandList();

  void printAvailableCreateCommandList();

  void printInputMessage();

  void printVoucherTypeMessage();
}
