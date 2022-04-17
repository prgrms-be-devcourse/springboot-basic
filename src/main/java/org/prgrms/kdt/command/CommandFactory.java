package org.prgrms.kdt.command;

import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

  private final InitCommand initCommand;
  private final ExitCommand exitCommand;
  private final BlacklistCommand blacklistCommand;
  private final ListVoucherCommand listVoucherCommand;
  private final CreateVoucherCommand createVoucherCommand;

  public CommandFactory(
      InitCommand initCommand,
      ExitCommand exitCommand,
      BlacklistCommand blacklistCommand,
      ListVoucherCommand listVoucherCommand,
      CreateVoucherCommand createVoucherCommand) {
    this.initCommand = initCommand;
    this.exitCommand = exitCommand;
    this.blacklistCommand = blacklistCommand;
    this.listVoucherCommand = listVoucherCommand;
    this.createVoucherCommand = createVoucherCommand;
  }

  public Command getCommand(CommandType commandType) {
    return switch (commandType) {
      case INIT -> initCommand;
      case EXIT -> exitCommand;
      case LIST -> listVoucherCommand;
      case BLACKLIST -> blacklistCommand;
      case CREATE -> createVoucherCommand;
    };
  }
}