package com.programmers.voucher.global.io.textio;

import com.programmers.voucher.domain.customer.dto.request.CustomerCreateRequest;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.dto.request.VoucherCreateRequest;
import com.programmers.voucher.domain.voucher.util.VoucherErrorMessages;
import com.programmers.voucher.global.io.ConsoleCommandType;
import com.programmers.voucher.global.io.ConsoleInput;
import com.programmers.voucher.global.util.CommonErrorMessages;
import org.beryx.textio.TextIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
            String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_VOUCHER_TYPE, val);
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
            String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_FIXED_AMOUNT, val);
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
            String errorMessage = CommonErrorMessages.addCurrentInput(INVALID_PERCENT_DISCOUNT, val);
            LOG.warn(errorMessage);
            messages.add(errorMessage);
        }
        return messages;
    }

    @Override
    public CustomerCreateRequest inputCustomerCreateInfo() {
        return null;
    }
}
