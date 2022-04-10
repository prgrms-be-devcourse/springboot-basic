package org.prgrms.kdt.command.service;

import java.util.Locale;
import org.prgrms.kdt.command.domain.Output;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput implements Output {

  private final MessageSource messageSource;

  public ConsoleOutput(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public void printLine(String message) {
    System.out.println(message);
  }

  @Override
  public void printMenu() {
    System.out.println(messageSource.getMessage("menu.introduction", null, Locale.ROOT));
  }
}