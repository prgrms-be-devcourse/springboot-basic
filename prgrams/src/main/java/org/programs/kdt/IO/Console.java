package org.programs.kdt.IO;

import org.programs.kdt.Customer.Customer;
import org.programs.kdt.Exception.ErrorCode;
import org.programs.kdt.Voucher.domain.Voucher;
import org.programs.kdt.Wallet.Wallet;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static org.programs.kdt.Exception.ErrorCode.INVALID_UUID;

@Component
public class Console implements Input, Output {

  private final Scanner scanner = new Scanner(System.in);

  @Override
  public void printVoucherList(List<Voucher> voucherList) {
    for (Voucher voucher : voucherList) {
      printVoucher(voucher);
      System.out.println();
    }
  }

  @Override
  public void output(String s) {
    System.out.println(s);
  }

  @Override
  public void printCustomerList(List<Customer> customerList) {
    for (Customer customer : customerList) {
      printCustomer(customer);
      System.out.println();
    }
  }

  @Override
  public void printCustomer(Customer customer) {
    System.out.println(
        MessageFormat.format(
            "id: {0}\nname: {1}\nemail: {2}\ncustomerType: {3}",
            customer.getCustomerId(),
            customer.getName(),
            customer.getEmail(),
            customer.getCustomerType().getType()));
  }

  @Override
  public void printVoucher(Voucher voucher) {
    System.out.println(
        MessageFormat.format(
            "id :{0}\ntype:{1}\nvalue: {2}",
            voucher.getVoucherId(), voucher.getVoucherType().getType(), voucher.getValue()));
  }

  @Override
  public void printWallet(Wallet wallet) {
    System.out.println(
        MessageFormat.format(
            "walletId: {0}\n"
                + "voucherId: {1}\nvoucherValue: {2}\n "
                + "customerId: {3}\ncustomerName: {4}\ncustomerEmail: {5}",
            wallet.getWalletId(),
            wallet.getVoucherId(),
            wallet.getVoucherValue(),
            wallet.getCustomerId(),
            wallet.getCustomerName(),
            wallet.getCustomerEmail()));
  }

  @Override
  public void printWalletList(List<Wallet> walletList) {
    for (Wallet wallet : walletList) {
      printWallet(wallet);
      System.out.println();
    }
  }

  @Override
  public void printError(ErrorCode errorCode) {
    System.out.println(errorCode.getMessage());
  }

  @Override
  public String input(String s) {
    System.out.println(s);
    return scanner.nextLine();
  }

  @Override
  public UUID inputUUID(String input) {
    UUID uuid = null;
    while (uuid == null) {
      try {
        uuid = UUID.fromString(input(input));

      } catch (IllegalArgumentException e) {
        output(INVALID_UUID.getMessage());
      }
    }
    return uuid;
  }
}
