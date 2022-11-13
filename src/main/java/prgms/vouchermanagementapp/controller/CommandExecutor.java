package prgms.vouchermanagementapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.blacklist.BlacklistManager;
import prgms.vouchermanagementapp.io.CommandType;
import prgms.vouchermanagementapp.io.IOManager;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.VoucherManager;
import prgms.vouchermanagementapp.voucher.VoucherType;

import java.text.MessageFormat;
import java.util.Optional;

@Component
public class CommandExecutor {

    private final IOManager ioManager;
    private final VoucherManager voucherManager;
    private final BlacklistManager blacklistManager;
    private final RunningState runningState;

    @Autowired
    public CommandExecutor(IOManager ioManager, VoucherManager voucherManager, BlacklistManager blacklistManager) {
        this.ioManager = ioManager;
        this.voucherManager = voucherManager;
        this.blacklistManager = blacklistManager;
        this.runningState = new RunningState();
    }

    public void run() {
        while (runningState.isRunning()) {
            Optional<CommandType> commandType = ioManager.askCommand();
            commandType.ifPresent(this::executeCommand);
        }
    }

    public void executeCommand(CommandType commandType) {
        if (commandType.is(CommandType.EXIT)) {
            runExit();
            return;
        }

        if (commandType.is(CommandType.CREATE)) {
            runCreate();
            return;
        }

        if (commandType.is(CommandType.LIST)) {
            runList();
        }

        if (commandType.is(CommandType.BLACKLIST)) {
            runBlacklist();
        }
    }

    private void runExit() {
        ioManager.notifyExit();
        runningState.exit();
    }

    private void runCreate() {
        Optional<VoucherType> voucherType = askVoucherType();
        voucherType.ifPresent(this::requestVoucherCreation);
    }

    private Optional<VoucherType> askVoucherType() {
        String voucherTypeIndex = ioManager.askVoucherTypeIndex();

        try {
            VoucherType voucherType = VoucherType.of(voucherTypeIndex);
            return Optional.of(voucherType);
        } catch (IllegalArgumentException exception) {
            ioManager.notifyErrorOccurred(MessageFormat.format("index ''{0}'' is invalid!!!", voucherTypeIndex));
            return Optional.empty();
        }
    }

    private void requestVoucherCreation(VoucherType voucherType) {
        if (voucherType.is(VoucherType.FixedAmountVoucher)) {
            Optional<Amount> fixedDiscountAmount = ioManager.askFixedDiscountAmount();
            fixedDiscountAmount.ifPresent(voucherManager::createVoucher);
        }

        if (voucherType.is(VoucherType.PercentDiscountVoucher)) {
            Optional<Ratio> fixedDiscountRatio = ioManager.askFixedDiscountRatio();
            fixedDiscountRatio.ifPresent(voucherManager::createVoucher);
        }
    }

    private void runList() {
        ioManager.notifyVouchers(voucherManager.findAllVouchers());
    }

    private void runBlacklist() {
        ioManager.showBlacklist(blacklistManager.getBlacklistFilepath());
    }
}
