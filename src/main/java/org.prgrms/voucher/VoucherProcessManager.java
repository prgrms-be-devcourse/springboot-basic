package org.prgrms.voucher;

import org.prgrms.console.Console;
import org.prgrms.exception.NoSuchVoucherTypeException;
import org.prgrms.voucher.discountType.Discount;
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

    boolean flag = true;

    while (flag) {
      try {
        generateAndSaveVoucher();
        flag = false;
      } catch (RuntimeException e) {
        console.printErrorMsg(e.getMessage());
      }
    }

  }

  private void generateAndSaveVoucher() {

    VoucherType voucherType = enteredVoucherType();

    Discount discount = enteredDiscountRate(voucherType);

    saveVoucher(voucherType, discount);
  }


  private VoucherType enteredVoucherType() {

    String inputType = console.chooseVoucherType();
    try {
      return VoucherType.of(inputType);
    } catch (NoSuchVoucherTypeException e) {
      throw new RuntimeException(e.getMessage());
    }

  }

  private Discount enteredDiscountRate(VoucherType voucherType) {
    String inputDiscountRate = console.enterDiscountRate(voucherType);
    return VoucherType.generateDiscount(voucherType, inputDiscountRate);
  }

  private void saveVoucher(VoucherType voucherType, Discount discount) {
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