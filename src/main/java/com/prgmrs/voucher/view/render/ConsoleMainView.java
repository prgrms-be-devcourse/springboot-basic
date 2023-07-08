package com.prgmrs.voucher.view.render;

import com.prgmrs.voucher.controller.BlacklistController;
import com.prgmrs.voucher.controller.VoucherController;
import com.prgmrs.voucher.dto.BlacklistResponse;
import com.prgmrs.voucher.dto.VoucherListResponse;
import com.prgmrs.voucher.enums.CreationType;
import com.prgmrs.voucher.enums.ListType;
import com.prgmrs.voucher.enums.ManagementType;
import com.prgmrs.voucher.exception.NoSuchChoiceException;
import com.prgmrs.voucher.setting.BlacklistProperties;
import com.prgmrs.voucher.view.ConsoleReader;
import com.prgmrs.voucher.view.writer.ConsoleCreationWriter;
import com.prgmrs.voucher.view.writer.ConsoleListWriter;
import com.prgmrs.voucher.view.writer.ConsoleMainWriter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleMainView implements CommandLineRunner {
    private final VoucherController voucherController;
    private final BlacklistController blackListController;
    private final ConsoleReader consoleReader;
    private final ConsoleMainWriter consoleMainWriter;
    private final ConsoleCreationWriter consoleCreationWriter;
    private final ConsoleListWriter consoleListWriter;
    private final ConsoleUserCreationView consoleUserCreationView;
    private final ConsoleVoucherCreationView consoleVoucherCreationView;
    private final ConsoleListView consoleListView;
    private final BlacklistProperties blacklistProperties;

    public ConsoleMainView(VoucherController voucherController, BlacklistController blackListController, ConsoleReader consoleReader, ConsoleMainWriter consoleMainWriter, ConsoleCreationWriter consoleCreationWriter, ConsoleListWriter consoleListWriter, ConsoleUserCreationView consoleUserCreationView, ConsoleVoucherCreationView consoleVoucherCreationView, ConsoleListView consoleListView, BlacklistProperties blacklistProperties) {
        this.voucherController = voucherController;
        this.blackListController = blackListController;
        this.consoleReader = consoleReader;
        this.consoleMainWriter = consoleMainWriter;
        this.consoleCreationWriter = consoleCreationWriter;
        this.consoleListWriter = consoleListWriter;
        this.consoleUserCreationView = consoleUserCreationView;
        this.consoleVoucherCreationView = consoleVoucherCreationView;
        this.consoleListView = consoleListView;
        this.blacklistProperties = blacklistProperties;
    }

    @Override
    public void run(String... args) {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleMainWriter.showManagementType();
            try {
                ManagementType managementType = ManagementType.of(consoleReader.read());
                continueRunning = redirectManagementType(managementType);
            } catch (NoSuchChoiceException e) {
                consoleCreationWriter.write("such option not exist");
            }
        }
    }

    private boolean redirectManagementType(ManagementType managementType) {
        boolean continueRunning = true;
        switch (managementType) {
            case CREATE_MODE -> selectCreationType();
            case LIST_MODE -> selectListType();
            case EXIT_THE_LOOP -> continueRunning = false;
        }
        return continueRunning;
    }

    private void selectCreationType() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleCreationWriter.showCreationType();
            try {
                CreationType creationType = CreationType.of(consoleReader.read());
                redirectCreationType(creationType);
                continueRunning = false;
            } catch (NoSuchChoiceException e) {
                consoleCreationWriter.write("such creation type not exist");
            }
        }
    }

    private void selectListType() {
        boolean continueRunning = true;
        while (continueRunning) {
            consoleListWriter.showListType();
            try {
                ListType listType = ListType.of(consoleReader.read());
                redirectListType(listType);
                continueRunning = false;
            } catch (NoSuchChoiceException e) {
                consoleCreationWriter.write("such list type not exist");
            }
        }
    }

    private void redirectCreationType(CreationType creationType) {
        switch (creationType) {
            case CREATE_USER -> consoleUserCreationView.createUser();
            case CREATE_VOUCHER -> consoleVoucherCreationView.selectVoucher();
        }
    }

    private void redirectListType(ListType listType) {
        switch (listType) {
            case SHOW_ENTIRE_LIST -> {
                VoucherListResponse voucherListResponse = voucherController.findAll();
                consoleListWriter.showList(voucherListResponse);
            }
            case SHOW_USER_LIST -> consoleListView.selectUser(listType);
            case SHOW_BLACKLIST -> {
                if (!blacklistProperties.isBlacklistAllow())
                    break;
                BlacklistResponse blacklist = blackListController.findAll();
                consoleListWriter.showBlacklist(blacklist);
            }
        }
    }
}
