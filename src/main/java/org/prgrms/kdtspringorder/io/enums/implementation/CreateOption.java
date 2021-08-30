package org.prgrms.kdtspringorder.io.enums.implementation;

import org.prgrms.kdtspringorder.io.enums.abstraction.Option;

public enum CreateOption implements Option {
  FIXED("fixed"), PERCENT("percent");

  private final String optionName;

  CreateOption(String optionName) {
    this.optionName = optionName;
  }

  @Override
  public String getOptionName() {
    return optionName;
  }
}
