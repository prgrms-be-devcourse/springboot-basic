package com.programmers.voucher.console;

import com.programmers.voucher.domain.Type;
import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import com.programmers.voucher.domain.voucher.VoucherEnum;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.util.UUID;

public class TextIoConsole implements Console {
    private TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public Type getCondition() {
        System.out.println();
        String stringType = textIO.newStringInputReader()
                .read("=== Voucher Program === \n" +
                        "Type exit to exit the program \n" +
                        "Type create to create a new voucher. \n" +
                        "Type list to list all vouchers. \n" +
                        "[enter type] : ");
        return Type.validateInput(stringType);
    }

    @Override
    public VoucherEnum getVoucherVersion() {
        System.out.println();
        Integer voucherVersion = textIO.newIntInputReader()
                .read("Choose version of Voucher\n" +
                                "Fixed AmountVoucher     : 1\n" +
                                "PercentDiscountVoucher  : 2\n" +
                                "[enter number of version]");
        return VoucherEnum.decideVoucherType(voucherVersion);
    }

    @Override
    public Voucher createFixedVoucher() {
        System.out.println();
        Integer amount = textIO.newIntInputReader()
                .read("Enter the amount of discount\n" +
                        "[amount of discount] : ");
        return new FixedAmountVoucher(UUID.randomUUID().toString().substring(0, 7), amount);
    }

    @Override
    public Voucher createPercentVoucher() {
        System.out.println();
        Integer rate = textIO.newIntInputReader()
                .read("Enter the rate of discount\n" +
                        "<<caution>> rate of discount means percentage versus original price\n" +
                        "ex) 70 => discount price = original price * (70 / 100)\n" +
                        "[rate of discount] : ");
        if (rate >= 100) {
            throw new IllegalArgumentException("rate cannot exceed 100 percent. Do you want FixedAmountVoucher?");
        }
        return new PercentDiscountVoucher(UUID.randomUUID().toString().substring(0, 7), rate);
    }
}
