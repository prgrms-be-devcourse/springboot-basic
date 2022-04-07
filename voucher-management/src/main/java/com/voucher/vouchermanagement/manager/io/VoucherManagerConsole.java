package com.voucher.vouchermanagement.manager.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import org.springframework.stereotype.Component;

@Component
public class VoucherManagerConsole implements VoucherManagerInput, VoucherManagerOutput {

  private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));


  @Override
  public String input(String prompt) throws IOException {
    bw.write(prompt);
    bw.flush();

    return br.readLine();
  }

  @Override
  public void println(String string) throws IOException {
    bw.write(string + "\n");
    bw.flush();
  }

  @Override
  public void printMenu() throws IOException {
    bw.write("=== Voucher Program ===" + "\n");
    bw.write("Type exit to exit program." + "\n");
    bw.write("Type create to create a new voucher." + "\n");
    bw.write("Type list to list all vouchers." + "\n");
    bw.write("Type blacklist to list all blacklist" + "\n");
    bw.flush();
  }

  @Override
  public void printVoucherType() throws IOException {
    bw.write("=== Voucher Type ===" + "\n");
    bw.write("1. fixedAmount" + "\n");
    bw.write("2. percentAmount" + "\n");
    bw.flush();
  }
}
