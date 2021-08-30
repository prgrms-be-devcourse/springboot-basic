package org.prgrms.kdtspringorder.io.enums.implementation;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import org.prgrms.kdtspringorder.io.enums.abstraction.Option;

public enum CommandType {
  CREATE("create", CreateOption.values(), true),
  LIST("list", new Option[]{}, false),
  EXIT("exit", new Option[]{}, false);

  private final String commandName;
  private final Option[] options;
  private final boolean mustNeedOption;

  CommandType(String commandName, Option[] options, boolean mustNeedOption) {
    this.commandName = commandName;
    this.options = options;
    this.mustNeedOption = mustNeedOption;
  }

  public boolean hasOption(String option) {
    long cnt = Arrays.stream(this.options)
        .filter(possibleOption -> possibleOption.getOptionName().equals(option))
        .count();
    return cnt > 0;
  }

  public boolean mustNeedOption() {
    return mustNeedOption;
  }

  public String getTypeName() {
    return this.commandName;
  }

  public static CommandType of(String commandName) {
    return Arrays.stream(CommandType.values())
        .filter(commandType -> commandName.equals(commandType.commandName))
        .findFirst()
        .orElseThrow(() -> {
          throw new IllegalArgumentException(
              MessageFormat.format("{} : 해당 명령어는 지원하지 않는 명령어 입니다.", commandName));
        });
  }

  public String[] getOptions() {
    return Arrays.stream(this.options)
        .map(Option::getOptionName)
        .toArray(String[]::new);
  }
}
