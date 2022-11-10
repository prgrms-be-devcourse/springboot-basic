package org.programmers.weekly.mission.util.io;

public interface Input {
    String getInput();
    String selectOption(String description);
    String selectVoucher(String description);
    Long getVoucherDiscount(String description);
}
