package org.prgrms.kdt.command;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class InitCommand implements Command {

  @Override
  public String execute() {
    return "Initialize the program";
  }
}