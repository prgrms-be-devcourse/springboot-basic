package com.example.commandlineapplication;

import com.example.commandlineapplication.domain.voucher.controller.VoucherController;
import com.example.commandlineapplication.global.io.Command;
import com.example.commandlineapplication.global.io.Console;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsoleRunner implements Runnable {

  private static final Logger LOG = LoggerFactory.getLogger(ConsoleRunner.class);
  private final Console console;
  private final VoucherController voucherController;

  @Override
  public void run() {
    boolean isRunning = true;

    while (isRunning) {
      console.printMenu();
      try {
        Command command = Command.find(console.input());

        isRunning = voucherController.handleCommand(command);
      } catch (Exception e) {
        LOG.error(e.getMessage() + e);
        return;
      }
    }
  }
}

