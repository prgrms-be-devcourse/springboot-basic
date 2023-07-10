package com.prgms.voucher.voucherproject.io;


import com.prgms.voucher.voucherproject.domain.FixedAmountVoucher;
import com.prgms.voucher.voucherproject.domain.PercentDiscountVoucher;
import com.prgms.voucher.voucherproject.domain.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Console implements Input, Output {
    private static final Scanner sc = new Scanner(System.in);
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    @Override
    public void printErrorMsg() {
        System.out.println("잘못된 명령어입니다.");
    }

    @Override
    public void printNoVoucher() {
        System.out.println("존재하는 바우처가 없습니다.");
    }

    @Override
    public void printMessage(String msg, boolean lnCheck) {
        if (lnCheck) {
            System.out.println(msg);
            return;
        }

        System.out.print(msg);

    }

    @Override
    public void printVoucherInfo(Voucher voucher) {
        if (PercentDiscountVoucher.isPercentDiscountVoucher(voucher)) {
            System.out.println("| UUID:" + voucher.getId() + "  | VoucherType: PercentVoucher | percent:" + ((PercentDiscountVoucher) voucher).getPercent() + " |");
        }
        if (FixedAmountVoucher.isFixedAmountVoucher(voucher)) {
            System.out.println("| UUID:" + voucher.getId() + "  | VoucherType: FixedVoucher | amount:" + ((FixedAmountVoucher) voucher).getAmount() + " |");
        }
    }

    @Override
    public String inputCommand() {
        return sc.nextLine();
    }

    public int inputIntType() {
        int selectedNum = -1;
        try {
            selectedNum = sc.nextInt();
        } catch (InputMismatchException e) {
            logger.error("VoucherType InputMismatchException");
            System.out.println(e.getLocalizedMessage());
        }
        sc.nextLine();
        return selectedNum;
    }

    @Override
    public long inputDiscountAmount() {
        return sc.nextLong();
    }

    @Override
    public String bufferDeleted() {
        return sc.nextLine();
    }
}
