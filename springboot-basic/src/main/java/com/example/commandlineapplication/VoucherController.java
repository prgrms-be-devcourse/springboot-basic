package com.example.commandlineapplication;

import com.example.commandlineapplication.io.Input;
import com.example.commandlineapplication.io.Output;

public class VoucherController implements Runnable {
  private final Input input;
  private final Output output;

  public VoucherController(Input input, Output output) {
    this.input = input;
    this.output = output;
  }


  @Override
  public void run() {
    while(true) {
      output.printMenu();
      String getMenu = input.input();
    }
  }
}
