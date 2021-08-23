package org.prgrms.kdt.io;

import org.prgrms.kdt.blackCustomer.domain.BlackCustomer;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component
public class IoInteraction {

    public void help() {
        System.out.println("=== Voucher Program ===");
        System.out.println("Type exit to exit the program.");
        System.out.println("Type create to create a new voucher");
        System.out.println("Type list to list all vouchers");
        System.out.println("Type black to list all black customer");

    }

    public void exit() {
        System.out.println("exit program");
    }

    public void create() {
        System.out.println("choose the NUMBER of voucher you want to make");
        // 처음에 아래처럼 했는데 그럼 종류가 추가될때마다 하드코딩이 되어야한다고 생각해서 enum클래스를 추가하였습니다.
        System.out.println("0.FixedAmountVoucher, 1.PercentDiscountVoucher");
    }

    public void discountRate() {
        System.out.println("Please enter a discount rate");
    }

    public void fail(String input) {
        System.out.println("'"+input+"'" + " is not a registerd command, See 'help'");
    }

    public void save() {
        System.out.println("create new voucher complete...");
    }

    public void list(List<Voucher> voucherList) {
        for (Voucher voucher : voucherList) {
            System.out.println(MessageFormat.format("UUID : {0} , discount : {1}, type : {2}",
                    voucher.getVoucherId(),
                    voucher.getDiscount(),
                    voucher.getVoucherType()));
        }
    }

    public void black(List<BlackCustomer> blackCustomerList) {
        for (BlackCustomer blackCustomer : blackCustomerList) {
            System.out.println(MessageFormat.format("UUID : {0} , name : {1}, phone : {2}",
                    blackCustomer.getCustomerId(),
                    blackCustomer.getName(),
                    blackCustomer.getPhone()));
        }
    }
}
