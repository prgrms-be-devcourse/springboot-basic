package prgms.vouchermanagementapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import prgms.vouchermanagementapp.exception.IllegalCommandException;
import prgms.vouchermanagementapp.exception.IllegalVoucherTypeIndexException;
import prgms.vouchermanagementapp.model.VoucherType;
import prgms.vouchermanagementapp.model.value.Amount;
import prgms.vouchermanagementapp.model.value.Ratio;
import prgms.vouchermanagementapp.service.VoucherService;
import prgms.vouchermanagementapp.view.CommandType;
import prgms.vouchermanagementapp.view.IoManager;

import java.text.MessageFormat;
import java.util.Optional;

@Component
public class CommandExecutor {

    private static final Logger log = LoggerFactory.getLogger(CommandExecutor.class);

    private final IoManager ioManager;
    private final VoucherService voucherService;
    private final CustomerController customerController;

    public CommandExecutor(IoManager ioManager, VoucherService voucherService, CustomerController customerController) {
        this.ioManager = ioManager;
        this.voucherService = voucherService;
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
        if (voucherType.is(VoucherType.FIXED_AMOUNT_VOUCHER)) {
            Optional<Amount> fixedDiscountAmount = ioManager.askFixedDiscountAmount();
            fixedDiscountAmount.ifPresent(voucherService::createVoucher);
        }

        if (voucherType.is(VoucherType.PERCENT_DISCOUNT_VOUCHER)) {
            Optional<Ratio> fixedDiscountRatio = ioManager.askFixedDiscountRatio();
            fixedDiscountRatio.ifPresent(voucherService::createVoucher);
        }
    }

    private void runList() {
        ioManager.showVoucherRecord(voucherService.findAllVouchers());
    }

    private void runBlacklist() {
        ioManager.showBlacklist();
    }
}
