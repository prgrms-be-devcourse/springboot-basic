package com.programmers.voucher.console;

import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PrinterImpl implements Printer {

    @Override
    public void printListOfVoucher(Map<String, Voucher> voucherList) {
        System.out.println();
        voucherList.forEach(
                (k, v) -> {
                    if (v instanceof FixedAmountVoucher) {
                        System.out.println("[FixedAmountVoucher | Voucher ID] : " + k + " | discount amount : " + ((FixedAmountVoucher) v).getAmount());
                    } else {
                        System.out.println("[PercentDiscountVoucher | ID] : " + k + " | discount percent : " + ((PercentDiscountVoucher) v).getPercent());
                    }
                }
        );
    }

    @Override
    public void printError(Exception e) {
        System.out.println();
        System.out.println("[Error Occurred] " + e.getMessage());
    }

    @Override
    public void printBlackList(List<String> blackList) {
        System.out.println();
        System.out.println(" === BlackList Customer === ");
        blackList.forEach(c -> System.out.println("- " + c));
    }

    @Override
    public void printEndMessage() {
        System.out.println("프로그램이 종료됩니다.");
    }
}
