package com.example.commandlineapplication.domain.voucher.controller;

import com.example.commandlineapplication.global.io.Command;
import com.example.commandlineapplication.global.io.Input;
import com.example.commandlineapplication.global.io.Output;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VoucherController implements Runnable {
  private final Input input;
  private final Output output;

  @Override
  public void run() {
    while(true) {
      output.printMenu();
      String getMenu = input.selectMenu();
      Command command = Command.from(getMenu);

      switch (command) {
        case EXIT:
          return;
        case CREATE:
          return;
        case LIST:
          return;
      }
    }
  }
}
