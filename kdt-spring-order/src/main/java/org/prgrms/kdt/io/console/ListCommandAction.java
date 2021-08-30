package org.prgrms.kdt.io.console;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.io.file.IO;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.MessageFormat;

public class ListCommandAction implements CommandAction {

    private final static Logger logger = LoggerFactory.getLogger(ListCommandAction.class);
    private final VoucherService voucherService;
    private final IO io;

    public ListCommandAction(VoucherService voucherService, IO io) {
        this.voucherService = voucherService;
        this.io = io;
    }

    @Override
    public void action() throws IOException{
        printVouchers();
    }

    private void printVouchers(){
        voucherService.vouchers() .forEach(this::printVoucher);
    }

    private void printVoucher(Voucher v) {
        try {
            printVoucher(v.toString());
        } catch (IOException e) {
            logger.error("Fail to print vouchers: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void printVoucher(String voucher) throws IOException {
        io.writeLine(voucher);
    }
}
