package com.programmers.voucher.console;

import com.programmers.voucher.domain.voucher.Voucher;

import java.util.List;
import java.util.Map;

public interface Printer {
    void printListOfVoucher(Map<String, Voucher> voucherList);
    void printError(Exception e);
    void printBlackList(List<String> blackList);
    void printEndMessage();
}
