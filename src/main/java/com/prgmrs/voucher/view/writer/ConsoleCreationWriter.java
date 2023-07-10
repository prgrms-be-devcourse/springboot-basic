package com.prgmrs.voucher.view.writer;

import com.prgmrs.voucher.dto.response.UserResponse;
import com.prgmrs.voucher.dto.response.VoucherResponse;
import com.prgmrs.voucher.enums.VoucherSelectionType;
import com.prgmrs.voucher.model.User;
import com.prgmrs.voucher.model.Voucher;
import com.prgmrs.voucher.model.strategy.FixedAmountDiscountStrategy;
import com.prgmrs.voucher.model.strategy.PercentDiscountStrategy;
import com.prgmrs.voucher.setting.VoucherProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class ConsoleCreationWriter {
    private final VoucherProperties voucherProperties;


    public ConsoleCreationWriter(VoucherProperties voucherProperties) {
        this.voucherProperties = voucherProperties;
    }

    public void write(String message) {
        System.out.println(message);
    }

    public void showCreationType() {
        write("=== Choosing creation type ===");
        write("Type 'voucher' to create a voucher.");
        write("Type 'user' to create a user.");
        write("Type 'back' to return to previous step");
    }

    public void showVoucherType() {
        write("=== Choosing voucher type ===");
        write("Type 'fixed' to create a voucher with fixed amount.");
        write("Type 'percent' to create a voucher with percent discount.");

    }

    public void showNamingUser() {
        write("=== Creating a user ===");
        write("Type any name to create a user");
    }

    public void showSpecificVoucherCreation(VoucherSelectionType voucherSelectionType) {
        if (VoucherSelectionType.FIXED_AMOUNT_VOUCHER == voucherSelectionType) {
            write("=== Creating Voucher with fixed amount ===");
            write(MessageFormat.format("Type amount to create a voucher with fixed amount. maximum value is {0}", voucherProperties.getMaximumFixedAmount()));
            return;
        }

        if (VoucherSelectionType.PERCENT_DISCOUNT_VOUCHER == voucherSelectionType) {
            write("=== Creating Voucher with percent discount ===");
            write("Type percent to create a voucher with percent discount. (1 to 100 without percent sign)");
        }
    }

    public void showVoucherResult(VoucherResponse voucherResponse) {
        Voucher voucher = voucherResponse.getVoucher();

        write("=== Successfully created a new voucher ===");
        if (voucher.getDiscountStrategy() instanceof FixedAmountDiscountStrategy fixedAmountDiscountStrategy) {
            write(MessageFormat.format("voucher id : {0}", voucher.getVoucherId()));
            write(MessageFormat.format("discount amount : {0}", fixedAmountDiscountStrategy.getAmount().getValue()));
            return;
        }
        if (voucher.getDiscountStrategy() instanceof PercentDiscountStrategy percentDiscountStrategy) {
            write(MessageFormat.format("voucher id : {0}", voucher.getVoucherId()));
            write(MessageFormat.format("discount percent : {0}%", percentDiscountStrategy.getPercent().getValue()));
        }
    }

    public void showUserResult(UserResponse userResponse) {
        User user = userResponse.getUser();

        write("=== Successfully created a new user ===");
        write(MessageFormat.format("user id : {0}", user.getUserId()));
        write(MessageFormat.format("name : {0}", user.getUsername()));
    }
}
