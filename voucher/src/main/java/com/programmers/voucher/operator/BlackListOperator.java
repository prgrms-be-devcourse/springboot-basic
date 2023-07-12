package com.programmers.voucher.operator;

import com.programmers.voucher.console.Printer;
import com.programmers.voucher.stream.blacklist.BlackListStream;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BlackListOperator {
    private final Printer printer;
    private final BlackListStream blackListStream;

    public BlackListOperator(Printer printer, BlackListStream blackListStream) {
        this.printer = printer;
        this.blackListStream = blackListStream;
    }

    public void showBlackList() throws IOException {
        printer.printBlackList(blackListStream.findAll());
    }
}
