package com.prgrms.kdt.springbootbasic.outputPackage;

import com.prgrms.kdt.springbootbasic.VoucherList;
import com.prgrms.kdt.springbootbasic.entity.voucher.FixedAmountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.PercentDiscountVoucher;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        System.out.println("\n=== Create New Voucher ===");
        List<String> voucherList = Stream.of(VoucherList.values()).map(v -> v.getClassName()).collect(Collectors.toList());

        for (int i = 0; i<voucherList.size(); i++){
            System.out.println(MessageFormat.format("Type {0} to create {1} Amount Voucher.", i, voucherList.get(i)));
        }
    }

    @Override
    public void printVoucherList(List<Voucher> voucherList) {
        if (voucherList.isEmpty()) {
            logger.error("[ConsoleOutput : printVoucherList] VoucherList is empty");
            System.out.println("Voucher List is empty");
            return;
        }

        voucherList.forEach(voucher -> {
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

    @Override
    public void wrongInput() {
        System.out.println("Wrong Input");
    }

    @Override
    public void errorOccurred() {
        System.out.println("Error Occured");
    }

}
