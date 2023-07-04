package com.programmers;

import com.programmers.domain.*;
import com.programmers.domain.voucher.Voucher;
import com.programmers.domain.voucher.VoucherType;
import com.programmers.domain.voucher.dto.VoucherCreateRequestDto;
import com.programmers.domain.voucher.dto.VouchersResponseDto;
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
        voucherService.save(new VoucherCreateRequestDto(voucher.getVoucherName(), voucher.getVoucherValue(), voucher.getVoucherType()));
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

        return VoucherType.createVoucher(voucherTypeInput, voucherName, discountValue);
    }

    public void getVoucherList() {
        console.printVoucherListTitle();
        VouchersResponseDto vouchersResponseDto = voucherService.findAll();
        console.printVouchers(vouchersResponseDto);
        log.info("The voucher list has been printed.");
    }

    private List<String> getBlacklist() {
        console.printBlacklistTitle();
        List<String> blacklist = blacklistService.findAll();
        console.printBlacklist(blacklist);
        log.info("The blacklist has been printed.");

        return blacklist;
    }
}
