package com.prgms.VoucherApp.view.console;

import com.prgms.VoucherApp.domain.customer.CustomerCommand;
import com.prgms.VoucherApp.domain.customer.CustomerStatus;
import com.prgms.VoucherApp.domain.voucher.VoucherCommand;
import com.prgms.VoucherApp.domain.voucher.VoucherType;
import com.prgms.VoucherApp.util.UUIDFormatChecker;
import com.prgms.VoucherApp.view.Input;
import com.prgms.VoucherApp.view.ManagementType;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ConsoleInputView implements Input {

    private static final Long DISCOUNT_AMOUNT_MIN_VALUE = 0L;
    private static final Long DISCOUNT_PERCENTAGE_MAX_VALUE = 100L;

    private static final Logger log = LoggerFactory.getLogger(ConsoleInputView.class);
    private final TextIO textIO;

    public ConsoleInputView() {
        textIO = TextIoFactory.getTextIO();
    }

    @Override
    public Integer inputManagementCommand() {
        return textIO.newIntInputReader()
            .withInputTrimming(true)
            .withInlinePossibleValues(ManagementType.getAllType())
            .withValueChecker((val, itemName) -> {
                if (!ManagementType.containsManagementType(val)) {
                    log.warn("inputCommand [{}] is invalid value", val);
                    return List.of("Please input a valid value.");
                }
                return null;
            })
            .read("명령어를 입력해주세요 >>");
    }

    @Override
    public Integer inputCustomerCommand() {
        return textIO.newIntInputReader()
            .withInputTrimming(true)
            .withInlinePossibleValues(CustomerCommand.getAllCustomerCommand())
            .withValueChecker((val, itemName) -> {
                if (!CustomerCommand.containsCustomerCommand(val)) {
                    log.warn("inputCustomerCommand [{}] is invalid value", val);
                    return List.of("Please input a valid value.");
                }
                return null;
            })
            .read("명령어를 입력해주세요 >>");
    }

    @Override
    public String inputCustomerStatus() {
        return textIO.newStringInputReader()
            .withInputTrimming(true)
            .withInlinePossibleValues(CustomerStatus.getAllCustomerStatus())
            .withValueChecker((val, itemName) -> {
                if (!CustomerStatus.containsCustomerStatus(val)) {
                    log.warn("inputCustomerStatus [{}] is invalid value", val);
                    return List.of("Please input a valid value.");
                }
                return null;
            })
            .read("고객 상태를 입력해주세요. >>");
    }

    @Override
    public String inputUUID() {
        return textIO.newStringInputReader()
            .withInputTrimming(true)
            .withValueChecker((val, itemName) -> {
                if (!UUIDFormatChecker.UUID_REGEX_COMPILE.matcher(val).matches()) {
                    log.warn("input UUID [{}] is invalid format", val);
                    return List.of("Please input UUID Format 00000000-0000-0000-0000-000000000000");
                }
                return null;
            })
            .read("도메인 ID를 입력해주세요. >>");
    }

    @Override
    public Integer inputVoucherCommand() {
        return textIO.newIntInputReader()
            .withInputTrimming(true)
            .withInlinePossibleValues(VoucherCommand.getAllVoucherCommand())
            .withValueChecker((val, itemName) -> {
                if (!VoucherCommand.containsVoucherCommand(val)) {
                    log.warn("inputVoucherCommand [{}] is invalid value", val);
                    return List.of("Please input a valid value.");
                }
                return null;
            })
            .read("명령어를 입력해주세요 >>");
    }

    @Override
    public String inputVoucherType() {
        return textIO.newStringInputReader()
            .withInputTrimming(true)
            .withInlinePossibleValues("fix", "percent")
            .withValueChecker((val, itemName) -> {
                if (!VoucherType.containsVoucherType(val)) {
                    log.warn("inputVoucherPolicy [{}] is invalid value", val);
                    return List.of("Please input a valid value.");
                }
                return null;
            })
            .read("명령어를 입력해주세요 >>");
    }

    @Override
    public Long inputDiscountAmount(VoucherType policy) {
        return switch (policy) {
            case FIXED_VOUCHER -> textIO.newLongInputReader()
                .withInputTrimming(true)
                .withMinVal(DISCOUNT_AMOUNT_MIN_VALUE)
                .withParseErrorMessagesProvider((val, itemName) -> {
                    log.warn("fixed Discount Amount is only Number, The entered value is [{}]", val);
                    return List.of("Please input a positive number");
                })
                .withValueChecker((val, itemName) -> {
                    if (val < DISCOUNT_AMOUNT_MIN_VALUE) {
                        log.warn("fixed Discount Amount is only Positive Number, The entered value is [{}]", val);
                        return List.of("Please input a positive number");
                    }
                    return null;
                })
                .read("할인 금액을 입력해주세요 >>");
            case PERCENT_VOUCHER -> textIO.newLongInputReader()
                .withInputTrimming(true)
                .withMinVal(DISCOUNT_AMOUNT_MIN_VALUE)
                .withMaxVal(DISCOUNT_PERCENTAGE_MAX_VALUE)
                .withParseErrorMessagesProvider((val, itemName) -> {
                    log.warn("PercentVoucher amount is only Number, The entered value is [{}]", val);
                    return List.of("Please input a positive number");
                })
                .withValueChecker((val, itemName) -> {
                    if (val < DISCOUNT_AMOUNT_MIN_VALUE || val > DISCOUNT_PERCENTAGE_MAX_VALUE) {
                        log.warn("PercentVoucher amount is only Positive Number and Less Than Or Equals 100, The entered value is [{}]", val);
                        return List.of("Please input a positive number and Less Than Or Equals 100");
                    }
                    return null;
                })
                .read("할인 비율을 입력해주세요 >>");
        };
    }
}
