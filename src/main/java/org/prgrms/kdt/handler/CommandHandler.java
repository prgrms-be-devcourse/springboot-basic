package org.prgrms.kdt.handler;

import static org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils.qualifiedBeanOfType;

import org.prgrms.kdt.command.Command;
import org.prgrms.kdt.command.CommandFactory;
import org.prgrms.kdt.command.CommandType;
import org.prgrms.kdt.io.Input;
import org.prgrms.kdt.io.Output;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class CommandHandler {

  private final Input input;
  private final Output output;
  private final ApplicationContext context;

  private final CommandFactory commandFactory;

  public CommandHandler(
      Input input,
      Output output,
      ApplicationContext context,
      CommandFactory commandFactory) {
    this.input = input;
    this.output = output;
    this.context = context;
    this.commandFactory = commandFactory;
  }

  public void handle() {
    var commandType = CommandType.INIT;
    while (commandType != CommandType.EXIT) {
      output.printMenu();
      commandType = CommandType.of(input.read());
      output.printLine(executeCommand(commandType));
    }
  }

  private String executeCommand(CommandType commandType) {
    Command command = commandFactory.getCommand(commandType);
    return command.execute();
  }
}