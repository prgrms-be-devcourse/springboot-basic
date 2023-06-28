package org.prgrms.kdt.commendLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ConsoleInput {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    private ConsoleInput() {
        throw new RuntimeException("생성 안돼!!");
    }

    public static String getUserMenu() throws IOException {
        return br.readLine();
    }

    public static String getVoucherTypes() throws IOException {
        System.out.print("1. FixedAmountVoucher 2. PercentDiscountVoucher\n " +
                "번호를 선택하세요:");
        return br.readLine();
    }
}
