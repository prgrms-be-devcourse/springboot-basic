package com.programmers;

import com.programmers.domain.*;
import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.VoucherType;
import com.programmers.io.Console;
import com.programmers.service.BlacklistService;
import com.programmers.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;

import static com.programmers.util.ValueFormatter.*;

@Controller
public class VoucherController {

    private static final Logger log = LoggerFactory.getLogger(VoucherController.class);

    private final Console console;
    private final VoucherService voucherService;
    private final BlacklistService blacklistService;

    public VoucherController(Console console, VoucherService voucherService, BlacklistService blacklistService) {
        this.console = console;
        this.voucherService = voucherService;
        this.blacklistService = blacklistService;
    }

    public void run() {
        boolean activated = true;
        log.info("The voucher program is activated.");

        while (activated) {
            console.printMenu();
            String command = console.readInput();
            Menu menu = Menu.findMenu(command);

            switch (menu) {
                case EXIT -> {
                    activated = false;
                    log.info("The program has been terminated.");
                }
                case CREATE -> createVoucher();
                case LIST -> getVoucherList();
                case BLACKLIST -> getBlacklist();
            }
        }
    }

    public Voucher createVoucher() {
        Voucher voucher = makeVoucher();
        voucherService.save(voucher);
        console.printVoucherCreated();
        log.info("The voucher has been created.");

        return voucher;
    }

    public Voucher makeVoucher() {
        console.printVoucherType();
        String voucherTypeInput = reformatVoucherType(console.readInput());

        console.printDiscountValueInput();
        Long discountValue = changeDiscountValueToNumber(console.readInput());

        console.printVoucherNameInput();
        String voucherName = console.readInput();

        return VoucherType.constructVoucher(voucherTypeInput, voucherName, discountValue);
    }

    public List<Voucher> getVoucherList() {
        console.printVoucherListTitle();
        List<Voucher> vouchers = voucherService.findAll();
        console.printVouchers(vouchers);
        log.info("The voucher list has been printed.");

        return vouchers;
    }

    private List<String> getBlacklist() {
        console.printBlacklistTitle();
        List<String> blacklist = blacklistService.findAll();
        console.printBlacklist(blacklist);
        log.info("The blacklist has been printed.");

        return blacklist;
    }
}
