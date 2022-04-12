package org.prgrms.vouchermanager.shell;

import org.prgrms.vouchermanager.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class VoucherManagerShell {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final VoucherService voucherService;

    public VoucherManagerShell(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @ShellMethod("create a voucher")
    public void create(String type, long amount) {
        try {
            voucherService.createVoucher(type, amount);
        } catch (RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    @ShellMethod("show voucher list")
    public String list() {
        return voucherService.allVouchersToString();
    }

}
