package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.BlacklistResponse;
import com.prgmrs.voucher.dto.VoucherListResponse;
import com.prgmrs.voucher.enums.ListSelectionType;
import com.prgmrs.voucher.exception.NoSuchChoiceException;
import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import com.prgmrs.voucher.view.writer.ConsoleListWriter;
import org.springframework.stereotype.Component;

@Component
public class ConsoleListView {
    private final VoucherController voucherController;
    private final ConsoleListWriter consoleListWriter;
    private final BlacklistController blacklistController;
    private final BlacklistProperties blacklistProperties;
    private final ConsoleReader consoleReader;
    private final ConsoleCreationWriter consoleCreationWriter;

    public ConsoleListView(VoucherController voucherController, ConsoleListWriter consoleListWriter, BlacklistController blacklistController, BlacklistProperties blacklistProperties, ConsoleReader consoleReader, ConsoleCreationWriter consoleCreationWriter) {
        this.voucherController = voucherController;
        this.consoleListWriter = consoleListWriter;
        this.blacklistController = blacklistController;
        this.blacklistProperties = blacklistProperties;
        this.consoleReader = consoleReader;
        this.consoleCreationWriter = consoleCreationWriter;
    }

    void selectListType() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleListWriter.showListType();
            try {
                ListSelectionType listSelectionType = ListSelectionType.of(consoleReader.read());
                redirectListType(listSelectionType);
                continueRunning = false;
            } catch (NoSuchChoiceException e) {
                consoleCreationWriter.write("such list type not exist");
            }
        }
    }

    private void redirectListType(ListSelectionType listSelectionType) {
        switch (listSelectionType) {
            case SHOW_ENTIRE_LIST -> {
                VoucherListResponse voucherListResponse = voucherController.findAll();
                consoleListWriter.showList(voucherListResponse, listSelectionType);
            }
            case SHOW_USER_LIST -> selectUser(listSelectionType);
            case SHOW_BLACKLIST -> {
                if (!blacklistProperties.isBlacklistAllow())
                    break;
                BlacklistResponse blacklist = blacklistController.findAll();
                consoleListWriter.showBlacklist(blacklist);
            }
            case BACK -> {}
        }
    }

    private void selectUser(ListSelectionType listSelectionType) {
        String name = "";
        VoucherListResponse voucherListResponse = voucherController.findByUsername(name);
        consoleListWriter.showList(voucherListResponse, listSelectionType);
    }
}
