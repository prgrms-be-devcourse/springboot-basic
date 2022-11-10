package org.prgrms.voucher;

import org.prgrms.console.Console;
import org.prgrms.exception.NoSuchVoucherTypeException;
import org.prgrms.voucher.discountType.Amount;
import org.prgrms.voucher.voucherType.Voucher;
import org.prgrms.voucher.voucherType.VoucherType;
import org.prgrms.voucherMemory.VoucherMemory;
import org.springframework.stereotype.Component;

@Component
public class VoucherProcessManager {

  private final Console console;

  private final VoucherMemory voucherMemory;

  public VoucherProcessManager(Console console, VoucherMemory voucherMemory) {
    this.console = console;
    this.voucherMemory = voucherMemory;
  }

  public void createVoucher() {

    boolean runningState = true;

    while (runningState) {
      try {
        VoucherType voucherType = enteredVoucherType();
        Amount discount = enteredAmount(voucherType);
        saveVoucher(voucherType, discount);

        runningState = false;
      } catch (RuntimeException e) {
        console.printErrorMsg(e.getMessage());
      }
    }

  }

  private VoucherType enteredVoucherType() {

    String inputType = console.chooseVoucherType();
    try {
      return VoucherType.of(inputType);
    } catch (NoSuchVoucherTypeException e) {
      throw new RuntimeException(e.getMessage());
    }

  }

  private Amount enteredAmount(VoucherType voucherType) {
    String enteredAmount = console.enteredAmount(voucherType);
    return VoucherType.generateAmount(voucherType, enteredAmount);
  }

  private void saveVoucher(VoucherType voucherType, Amount discount) {
    Voucher voucherSaved = voucherMemory.save(
        VoucherType.generateVoucher(voucherType, discount));
    console.printSavedVoucher(voucherSaved);
  }

  public void exit() {
    VoucherExecution.stop();
  }

  public void showVoucherList() {
    console.printVoucherList(voucherMemory.findAll());
  }


}