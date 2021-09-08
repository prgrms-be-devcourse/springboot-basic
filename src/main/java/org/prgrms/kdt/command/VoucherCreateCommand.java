package org.prgrms.kdt.command;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.prgrms.kdt.model.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.prgrms.kdt.util.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VoucherCreateCommand implements CommandOperator {

    private static final Logger logger = LoggerFactory.getLogger(VoucherCreateCommand.class);

    private final VoucherService voucherService;
    private final Console console;
    private final CommandType commandType;
    private final Function<String, VoucherType> voucherTypeLookupFunc;


    public VoucherCreateCommand(VoucherService voucherService,
        Console console) {
        this.voucherService = voucherService;
        this.console = console;
        this.commandType = CommandType.VOUCHER_CREATE;
        this.voucherTypeLookupFunc = EnumUtils.lookupMap(VoucherType.values(), VoucherType::getNum);
    }

    @Override
    public void execute() {
        console.printMessage("=== Creating a new voucher ===");

        Optional<VoucherType> voucherType = Optional.empty();
        var digitPredicate = Pattern.compile("^[0-9]*$").asPredicate();
        while (voucherType.isEmpty()) {
            console.printMessage("Type voucher type number");
            console.printEnumValues(VoucherType.values());
            var typeNum = console.input(digitPredicate);
            voucherType = getVoucherType(typeNum);
        }

        var value = console.input("How much discount?: ", digitPredicate);
        var createdVoucher = voucherService.createVoucher(
            voucherType.get().create(Long.valueOf(value)));
        logger.info(MessageFormat.format("user create voucher: {0}", createdVoucher));
    }

    public Optional<VoucherType> getVoucherType(String typeNum) {
        return Optional.ofNullable(voucherTypeLookupFunc.apply(typeNum));
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }
}
