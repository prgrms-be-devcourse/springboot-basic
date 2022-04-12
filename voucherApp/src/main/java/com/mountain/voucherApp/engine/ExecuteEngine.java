package com.mountain.voucherApp.engine;

import com.mountain.voucherApp.engine.create.CreateStrategy;
import com.mountain.voucherApp.engine.exit.ExitStrategy;
import com.mountain.voucherApp.engine.list.ListStrategy;
import com.mountain.voucherApp.enums.DiscountPolicy;
import com.mountain.voucherApp.io.Console;
import com.mountain.voucherApp.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.LineNumberReader;

import static com.mountain.voucherApp.utils.MenuUtil.*;

@Component
public class ExecuteEngine {

    private final Logger log = LoggerFactory.getLogger(ExecuteEngine.class);

    private final Console console;
    private final CreateStrategy createStrategy;
    private final ExitStrategy exitStrategy;
    private final ListStrategy listStrategy;

    @Autowired
    public ExecuteEngine(Console console, CreateStrategy createStrategy, ExitStrategy exitStrategy, ListStrategy listStrategy) {
        this.console = console;
        this.createStrategy = createStrategy;
        this.exitStrategy = exitStrategy;
        this.listStrategy = listStrategy;
    }

    private void create() {
        createStrategy.create();
    }

    private void list() {
        listStrategy.list();
    }

    private void exit() {
        exitStrategy.exit();
    }

    public void run() {
        while (true) {
            console.printManual();
            String command = console.input().toLowerCase().trim();
            if (isExit(command)) {
                log.info("program exit");
                exit();
                break;
            } else if (isCreate(command)) {
                log.info("create new voucher");
                create();
            } else if (isList(command)) {
                log.info("select voucher list");
                list();
            } else {
                log.error("wrong input error");
                console.printWrongInput();
            }
        }
    }
}
