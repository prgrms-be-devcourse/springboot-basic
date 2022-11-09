package com.prgrms.springbootbasic.voucher;

import com.prgrms.springbootbasic.common.exception.AmountOutOfBoundException;
import com.prgrms.springbootbasic.common.exception.InvalidVoucherTypeException;
import com.prgrms.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.prgrms.springbootbasic.voucher.domain.PercentVoucher;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import com.prgrms.springbootbasic.voucher.dto.VoucherInfo;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntPredicate;

//convention
public enum VoucherType {
    FIXED_AMOUNT("fixed amount",
            amount -> amount < 1 || amount > 10000,
            voucherInfo -> new FixedAmountVoucher(voucherInfo.getType().getInputValue(), voucherInfo.getAmount())),
    PERCENT("percent",
            amount -> amount < 1 || amount > 99,
            voucherInfo -> new PercentVoucher(voucherInfo.getType().getInputValue(), voucherInfo.getAmount()));

    private final String inputValue;
    private final IntPredicate amountOutOfBound;
    private final Function<VoucherInfo, Voucher> voucherConstructor;

    VoucherType(String inputValue, IntPredicate amountOutOfBound, Function<VoucherInfo, Voucher> voucherConstructor) {
        this.inputValue = inputValue;
        this.amountOutOfBound = amountOutOfBound;
        this.voucherConstructor = voucherConstructor;
    }

    public String getInputValue() {
        return inputValue;
    }

    public static VoucherType findByInputValue(String inputValue) {
        return Arrays.stream(values())
                .filter(voucherType -> voucherType.getInputValue().equals(inputValue))
                .findFirst()
                .orElseThrow(() -> new InvalidVoucherTypeException(
                    MessageFormat.format("Input voucher {0} is invalid. Please select again.", inputValue)));
    }

    public int validateAmount(String amountInput) {
        try{
            int amount = Integer.parseInt(amountInput);
            if (amountOutOfBound.test(amount)){
                throw new AmountOutOfBoundException(MessageFormat.format("Amount: {0}", amount));
            }
            return amount;
        } catch(NumberFormatException e){
            throw new NumberFormatException(MessageFormat.format("Your input {0} is not integer.", amountInput));
        }
    }

    public Voucher construct(VoucherInfo voucherInfo){
        return voucherConstructor.apply(voucherInfo);
    }
}
