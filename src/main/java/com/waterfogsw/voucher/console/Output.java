package com.waterfogsw.voucher.console;

import java.util.List;

public interface Output {
    void printBlackList(List<String> blackList);
    void printPathError();
    void printMenuError();
}
