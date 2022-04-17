package org.prgrms.kdt.command;

import org.springframework.stereotype.Component;

@Component
public class CommandFactory {

  private final InitCommand initCommand;
  private final ExitCommand exitCommand;
  private final ErrorCommand errorCommand;
  private final BlacklistCommand blacklistCommand;
  private final ListVoucherCommand listVoucherCommand;
  private final CreateVoucherCommand createVoucherCommand;

  public CommandFactory(
      InitCommand initCommand,
      ExitCommand exitCommand,
      ErrorCommand errorCommand,
      BlacklistCommand blacklistCommand,
      ListVoucherCommand listVoucherCommand,
      CreateVoucherCommand createVoucherCommand) {
    this.initCommand = initCommand;
    this.exitCommand = exitCommand;
    this.errorCommand = errorCommand;
    this.blacklistCommand = blacklistCommand;
    this.listVoucherCommand = listVoucherCommand;
    this.createVoucherCommand = createVoucherCommand;
  }

  public Command getCommand(CommandType commandType) {
    return switch (commandType) {
      case INIT -> initCommand;
      case EXIT -> exitCommand;
      case ERROR -> errorCommand;
      case BLACKLIST -> blacklistCommand;
      case LIST -> listVoucherCommand;
      case CREATE -> createVoucherCommand;
    };
  }
}