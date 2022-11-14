package prgms.vouchermanagementapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.exception.IllegalCommandException;
import prgms.vouchermanagementapp.exception.IllegalVoucherTypeIndexException;
import prgms.vouchermanagementapp.io.CommandType;
import prgms.vouchermanagementapp.io.IOManager;
import prgms.vouchermanagementapp.model.Amount;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.VoucherManager;
import prgms.vouchermanagementapp.voucher.VoucherType;

import java.util.Optional;

@Component
public class CommandExecutor {

    private static final Logger log = LoggerFactory.getLogger(CommandExecutor.class);

    private final IOManager ioManager;
    private final VoucherManager voucherManager;
    private final RunningState runningState;

    public CommandExecutor(IOManager ioManager, VoucherManager voucherManager) {
        this.ioManager = ioManager;
        this.voucherManager = voucherManager;
        this.runningState = new RunningState();
    }

    public void run() {
        while (runningState.isRunning()) {
            try {
                String command = ioManager.askCommand();
                CommandType.of(command)
                        .ifPresent(this::executeCommand);
            } catch (IllegalCommandException e) {
                log.warn("command input error occurred: {}", e.getMessage());
                ioManager.notifyErrorOccurred(e.getMessage());
            }
        }
    }

    public void executeCommand(CommandType commandType) {
        switch (commandType) {
            case EXIT -> runExit();
            case CREATE -> runCreate();
            case LIST -> runList();
            case BLACKLIST -> runBlacklist();
            default -> {
                log.error("Error: commandType mismatch error occurred while executing command");
                throw new RuntimeException();
            }
        }
    }

    private void runExit() {
        ioManager.notifyExit();
        runningState.exit();
    }

    private void runCreate() {
        String voucherTypeIndex = ioManager.askVoucherTypeIndex();
        Optional<VoucherType> voucherType = VoucherType.of(voucherTypeIndex);

        if (voucherType.isEmpty()) {
            ioManager.notifyErrorOccurred(new IllegalVoucherTypeIndexException(voucherTypeIndex).getMessage());
        }
        voucherType.ifPresent(this::requestVoucherCreation);
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
        ioManager.showBlacklist();
    }
}
