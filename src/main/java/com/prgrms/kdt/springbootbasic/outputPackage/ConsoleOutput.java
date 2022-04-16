package com.prgrms.kdt.springbootbasic.outputPackage;

import com.prgrms.kdt.springbootbasic.entity.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.entity.Voucher;
import com.prgrms.kdt.springbootbasic.inputPackage.ConsoleInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
@Profile("console")
public class ConsoleOutput implements CustomOutput{
    private static final Logger logger = LoggerFactory.getLogger(ConsoleOutput.class);

    @Override
    public void informCommandWithConsole() {
        System.out.println("\n=== Voucher Program ===\n" +
                "Type exit to exit the program.\n" +
                "Type create to create a new voucher.\n" +
                "Type list to list all vouchers.\n");

    }

    @Override
    public void informNewVoucherInfo() {
        System.out.println("\n=== Create New Voucher ===\n"+
                "Type 1 to create Fixed Amount Voucher\n"+
                "Type 2 to create Percent Amount Voucher\n");
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        if (voucherList.size() == 0) {
            logger.error("[ConsoleOutput : printVoucherList] VoucherList is empty");
            System.out.println("Voucher List is empty");
            return;
        }

        voucherList.stream().forEach(voucher -> {
            if (voucher instanceof FixedAmountVoucher)
                System.out.println(MessageFormat.format("Voucher Id : {0}\t Discount Amount : {1}", voucher.getVoucherId(), voucher.getDiscountAmount()));
            else if (voucher instanceof PercentDiscountVoucher)
                System.out.println(MessageFormat.format("Voucher Id : {0}\t Discount Percentage : {1}", voucher.getVoucherId(), voucher.getDiscountAmount()));
        });
    }

    @Override
    public void printError(String error) {
        System.out.println(error);
    }

    @Override
    public void informProcessEnd() {
        System.out.println("정상적으로 처리되었습니다.");
        System.out.println("\n****************************************\n");
    }


}
