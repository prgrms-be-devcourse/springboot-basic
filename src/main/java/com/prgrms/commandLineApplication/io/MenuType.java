package com.prgrms.commandLineApplication.io;

import java.util.NoSuchElementException;

public enum MenuType {
  EXIT,
  VOUCHERLIST,
  CREATEVOUCHER,
  CUSTOMERLIST,
  CREATECUSTOMER;

  private static final String MENU_TYPE_ERROR = "Invalid Menu Type";

  public static MenuType valueOfType(String menu) {
    try {
      return MenuType.valueOf(menu.toUpperCase());
    } catch (Exception e) {
      throw new NoSuchElementException(MENU_TYPE_ERROR);
    }
  }

}
