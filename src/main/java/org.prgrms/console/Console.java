package org.prgrms.console;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class Console {

  private final Message message;
  private final Scanner scanner = new Scanner(System.in);


  public Console(Message message) {
    this.message = message;
  }

  public String choiceMenu() {
    printCommands();
    return ConvertInputToLowercase();
  }

  private String ConvertInputToLowercase() {
    return scanner.nextLine().toLowerCase().trim();
  }

  private void printCommands() {
    System.out.println(message.showSupportedCommands());
  }

  public void commandNotFound() {
    System.out.println(message.doNotMatch());
  }


}
