package org.prgrms.kdt.app.command;

import static java.util.UUID.fromString;

import java.util.Optional;
import java.util.regex.Pattern;
import org.prgrms.kdt.app.io.Console;
import org.prgrms.kdt.model.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.util.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherUpdateTypeCommand implements CommandOperator{
    private static final Logger logger = LoggerFactory.getLogger(VoucherUpdateTypeCommand.class);

    private final VoucherService voucherService;
    private final Console console;
    private final CommandType commandType;
    private final VoucherListCommand voucherListCommand;


    public VoucherUpdateTypeCommand(VoucherService voucherService,
        Console console, VoucherListCommand voucherListCommand) {
        this.voucherService = voucherService;
        this.console = console;
        this.voucherListCommand = voucherListCommand;
        this.commandType = CommandType.UPDATE_VOUCHER_TYPE;
    }

    @Override
    public void execute() {
        voucherListCommand.execute();
        console.printMessage("=== Update Voucher Type ===");
        var input = console.input("Type voucher id you want to modify");
        var voucherId = fromString(input);
        var voucher = voucherService.getVoucher(voucherId);

        Optional<VoucherType> voucherType = Optional.empty();
        var digitPredicate = Pattern.compile("^[0-9]*$").asPredicate();
        while (voucherType.isEmpty()) {
            console.printMessage("Type voucher type number you want to modify");
            console.printEnumValues(VoucherType.values());
            var typeNum = console.input(digitPredicate);
            voucherType = EnumUtils.getVoucherType(typeNum);
        }
        var updatedVoucher = voucherService.updateVoucherType(voucher, voucherType.get());
        logger.info("updated voucher: {}" ,updatedVoucher);

    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}
