package com.prgms.voucher.voucherproject;

import com.prgms.voucher.voucherproject.App.CustomerApp;
import com.prgms.voucher.voucherproject.App.VoucherApp;
import com.prgms.voucher.voucherproject.io.Console;
import com.prgms.voucher.voucherproject.io.Constant;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProjectApp implements CommandLineRunner {

    private final Console console = new Console();
    private final VoucherApp voucherApp;
    private final CustomerApp customerApp;

    public ProjectApp(VoucherApp voucherApp, CustomerApp customerApp) {
        this.voucherApp = voucherApp;
        this.customerApp = customerApp;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean isRunning = true;

        while (isRunning) {
            AppType appType = console.inputAppService();

            if (appType == null) continue;

            switch (appType) {
                case VOUCHER -> voucherApp.voucherRun();
                case CUSTOMER -> customerApp.customerRun();
                case EXIT -> {
                    isRunning = false;
                    console.printMessage(Constant.PROGRAM_END, true);
                }
            }
        }
    }
}
