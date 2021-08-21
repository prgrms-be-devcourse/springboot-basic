package com.example.kdtspringmission.view;

import com.example.kdtspringmission.voucher.domain.Voucher;
import java.util.List;

public class ConsoleOutputView implements OutputView{

    @Override
    public void commandList() {
        System.out.println("=== Voucher Program ===\n"
            + "Type 'exit' to exit the program\n"
            + "Type 'create' to create voucher\n"
            + "Type 'list' to list vouchers");
    }

    @Override
    public void creatableVoucherList() {
        System.out.println("Which one? (1.FixedAmountVoucher, 2.RateAmountVoucher)");
    }

    @Override
    public void voucherList(List<Voucher> vouchers) {
        System.out.println(vouchers);
    }
}
