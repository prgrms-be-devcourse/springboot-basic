package com.prgms.voucher.voucherproject;

import com.prgms.voucher.voucherproject.config.AppConfiguration;
import com.prgms.voucher.voucherproject.io.Console;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class VoucherApp implements Runnable{

    private final Console console = new Console();

    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        applicationContext.register(AppConfiguration.class);

        VoucherApp voucherApp = new VoucherApp();
        voucherApp.run();
    }

    @Override
    public void run() {
        console.printMenu();
    }
}
