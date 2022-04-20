package org.prgrms.vouchermanager.shell;

import org.prgrms.vouchermanager.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.text.MessageFormat;

@ShellComponent
public class VoucherManagerShell {

    private final Logger log = LoggerFactory.getLogger(VoucherManagerShell.class);
    private final VoucherService voucherService;

    public VoucherManagerShell(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @ShellMethod("create a voucher")
    public String create(String type, long amount) {
        try {
            voucherService.createVoucher(type, amount);
            return MessageFormat.format("{0} voucher created.", type);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
        return "fail to create a voucher.";
    }

    @ShellMethod("show voucher list")
    public String list() {
        return voucherService.allVouchersToString();
    }
}
