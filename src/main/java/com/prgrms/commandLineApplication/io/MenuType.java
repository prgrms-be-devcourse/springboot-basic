package com.prgrms.commandLineApplication.io;

import com.prgrms.commandLineApplication.voucher.validator.VoucherValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum MenuType {
  EXIT,
  LIST,
  CREATE;

  private static final Logger LOGGER = LoggerFactory.getLogger(MenuType.class);

  private static final String MENU_TYPE_ERROR = "Invalid Menu Type";

  public static MenuType valueOfType(String menu) {
    try {
      return MenuType.valueOf(menu.toUpperCase());
    } catch (Exception e) {
      LOGGER.error("Menu Error Message => {}", MENU_TYPE_ERROR);
      throw new IllegalArgumentException(MENU_TYPE_ERROR);
    }
  }

}
