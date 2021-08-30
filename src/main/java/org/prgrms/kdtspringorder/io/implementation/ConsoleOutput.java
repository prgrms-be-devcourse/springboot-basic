package org.prgrms.kdtspringorder.io.implementation;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import org.prgrms.kdtspringorder.io.abstraction.Input;
import org.prgrms.kdtspringorder.io.abstraction.Output;
import org.prgrms.kdtspringorder.io.domain.Command;
import org.prgrms.kdtspringorder.voucher.domain.Voucher;


public class ConsoleOutput implements Output {

  @Override
  public void printVoucherList(List<Voucher> listToPrint) {
    listToPrint.forEach(this::printVoucher);
  }

  @Override
  public void printVoucher(Voucher voucher) {
    StringBuilder stringBuilder = new StringBuilder("");
    stringBuilder
        .append("------------------------------------------------------------")
        .append("\nVoucher ID : ").append(voucher.getId())
        .append("\nVoucher Type : ").append(voucher.getVoucherTypeInString())
        .append("\n------------------------------------------------------------");
    System.out.println(
        stringBuilder.toString()
    );
  }

  @Override
  public void print(String string) {
    System.out.println(string);
  }

}
