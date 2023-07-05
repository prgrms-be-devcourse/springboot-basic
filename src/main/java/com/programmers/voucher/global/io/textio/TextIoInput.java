package com.programmers.voucher.global.io.textio;

import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.customer.dto.request.CustomerUpdateRequest;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.util.VoucherErrorMessages;
import com.programmers.voucher.global.io.ConsoleInput;
import com.programmers.voucher.global.io.command.ConsoleCommandType;
import com.programmers.voucher.global.io.command.CustomerCommandType;
import com.programmers.voucher.global.io.command.VoucherCommandType;
import org.beryx.textio.TextIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.*;

import static com.programmers.voucher.domain.voucher.util.VoucherDiscountRange.*;
import static com.programmers.voucher.domain.voucher.util.VoucherErrorMessages.*;
import static com.programmers.voucher.global.util.ConsoleMessages.*;

@Component
public class TextIoInput implements ConsoleInput {
    private static final Logger LOG = LoggerFactory.getLogger(TextIoInput.class);

    private final TextIO textIO;

    public TextIoInput(TextIO textIO) {
        this.textIO = textIO;
    }

    @Override
    public ConsoleCommandType inputInitialCommand() {
        String command = textIO.newStringInputReader()
                .read();

        return ConsoleCommandType.getValue(command);
    }

    @Override
    public VoucherCreateRequest inputVoucherCreateInfo() {
        VoucherType voucherType = inputVoucherType();
        switch (voucherType) {
            case FIXED_AMOUNT -> {
                long amount = inputFixedAmount();
                return new VoucherCreateRequest(voucherType, amount);
            }
            case PERCENT -> {
                long percent = inputPercentDiscount();
                return new VoucherCreateRequest(voucherType, percent);
            }
        }

        throw new IllegalStateException(VoucherErrorMessages.UNHANDLED_VOUCHER_TYPE);
    }

    private VoucherType inputVoucherType() {
        String rawVoucherType = textIO.newStringInputReader()
                .withValueChecker((val, itemName) -> voucherTypeValidateErrorMessages(val))
                .read(VOUCHER_TYPES);

        return VoucherType.getValue(rawVoucherType);
    }

    private List<String> voucherTypeValidateErrorMessages(String val) {
        boolean invalidVoucherType = Arrays.stream(VoucherType.values())
                .map(VoucherType::getType)
                .noneMatch(rawType -> Objects.equals(rawType, val));

        List<String> messages = new ArrayList<>();
        if (invalidVoucherType) {
            String errorMessage = MessageFormat.format(INVALID_VOUCHER_TYPE, val);

            LOG.warn(errorMessage);
            messages.add(errorMessage);
        }
        return messages;
    }

    private long inputFixedAmount() {
        return textIO.newLongInputReader()
                .withValueChecker((val, itemName) -> fixedAmountValidateErrorMessages(val))
                .read(AMOUNT);
    }

    private List<String> fixedAmountValidateErrorMessages(Long val) {
        List<String> messages = new ArrayList<>();
        if (val <= FIXED_AMOUNT_MIN) {
            String errorMessage = MessageFormat.format(INVALID_FIXED_AMOUNT, val);

            LOG.warn(errorMessage);
            messages.add(errorMessage);
        }
        return messages;
    }

    private long inputPercentDiscount() {
        return textIO.newLongInputReader()
                .withValueChecker((val, itemName) -> percentDiscountValidateErrorMessages(val))
                .read(PERCENT);
    }

    private List<String> percentDiscountValidateErrorMessages(Long val) {
        List<String> messages = new ArrayList<>();
        if (val <= PERCENT_DISCOUNT_MIN || val >= PERCENT_DISCOUNT_MAX) {
            String errorMessage = MessageFormat.format(INVALID_PERCENT_DISCOUNT, val);

            LOG.warn(errorMessage);
            messages.add(errorMessage);
        }
        return messages;
    }

    @Override
    public CustomerCreateRequest inputCustomerCreateInfo() {
        String email = textIO.newStringInputReader()
                .read(ENTER_EMAIL);

        String name = textIO.newStringInputReader()
                .read(ENTER_NAME);

        return new CustomerCreateRequest(email, name);
    }

    @Override
    public CustomerUpdateRequest inputCustomerUpdateInfo() {
        UUID customerId = inputUUID();

        String newName = textIO.newStringInputReader()
                .read(ENTER_NEW_NAME);

        boolean banned = textIO.newBooleanInputReader()
                .withTrueInput(BAN)
                .withFalseInput(UNBAN)
                .read(CHOOSE_BAN);

        return new CustomerUpdateRequest(customerId, newName, banned);
    }

    @Override
    public UUID inputUUID() {
        String uuid = textIO.newStringInputReader()
                .read(ENTER_ID);

        return UUID.fromString(uuid);
    }

    @Override
    public VoucherCommandType inputVoucherCommandType() {
        String command = textIO.newStringInputReader()
                .read();

        return VoucherCommandType.getValue(command);
    }

    @Override
    public CustomerCommandType inputCustomerCommandType() {
        String command = textIO.newStringInputReader()
                .read();

        return CustomerCommandType.getValue(command);
    }
}
