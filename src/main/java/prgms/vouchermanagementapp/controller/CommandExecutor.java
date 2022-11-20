package prgms.vouchermanagementapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.domain.VoucherType;
import prgms.vouchermanagementapp.domain.value.Amount;
import prgms.vouchermanagementapp.domain.value.Ratio;
import prgms.vouchermanagementapp.exception.IllegalCommandException;
import prgms.vouchermanagementapp.exception.IllegalVoucherTypeIndexException;
import prgms.vouchermanagementapp.service.VoucherManager;
import prgms.vouchermanagementapp.view.CommandType;
import prgms.vouchermanagementapp.view.IoManager;

import java.text.MessageFormat;
import java.util.Optional;

@Component
public class CommandExecutor {

    private static final Logger log = LoggerFactory.getLogger(CommandExecutor.class);

    private final IoManager ioManager;
    private final VoucherManager voucherManager;
    private final CustomerController customerController;

    public CommandExecutor(IoManager ioManager, VoucherManager voucherManager, CustomerController customerController) {
        this.ioManager = ioManager;
        this.voucherManager = voucherManager;
        this.customerController = customerController;
    }

    public void run(RunningState runningState) {

        while (runningState.isRunning()) {
            try {
                String command = ioManager.askCommand();
                CommandType.of(command)
                        .ifPresent(commandType -> executeCommand(commandType, runningState));
            } catch (IllegalCommandException illegalCommandException) {
                ioManager.notifyErrorOccurred(illegalCommandException.getMessage());
            }
        }
    }

    public void executeCommand(CommandType commandType, RunningState runningState) {
        switch (commandType) {
            case EXIT -> runExit(runningState);
            case CREATE -> runCreate();
            case LIST -> runList();
            case BLACKLIST -> runBlacklist();
            default -> {
                throw new IllegalArgumentException(
                        MessageFormat.format("Command Type ''{0}'' is invalid.", commandType)
                );
            }
        }
    }

    private void runExit(RunningState runningState) {
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
        ioManager.showVoucherRecord(voucherManager.findAllVouchers());
    }

    private void runBlacklist() {
        ioManager.showBlacklist();
    }
}
