package com.programmers.voucher.console;

import com.programmers.voucher.domain.customer.Customer;
import com.programmers.voucher.domain.voucher.FixedAmountVoucher;
import com.programmers.voucher.domain.voucher.PercentDiscountVoucher;
import com.programmers.voucher.domain.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PrinterImpl implements Printer {

    @Override
    public void printVoucher(Voucher voucher) {
        System.out.println();
        printFixedAmountVoucher(voucher);
        printPercentDiscountVoucher(voucher);
    }

    private static void printPercentDiscountVoucher(Voucher voucher) {
        if (voucher instanceof PercentDiscountVoucher) {
            System.out.println("[PercentDiscountVoucher | ID] : " + voucher.getVoucherId() + " | discount percent : " + ((PercentDiscountVoucher) voucher).getRate());
        }
    }

    private static void printFixedAmountVoucher(Voucher voucher) {
        if (voucher instanceof FixedAmountVoucher) {
            System.out.println("[FixedAmountVoucher | Voucher ID] : " + voucher.getVoucherId() + " | discount amount : " + ((FixedAmountVoucher) voucher).getAmount());
        }
    }

    @Override
    public void printListOfVoucher(Map<String, Voucher> voucherList) {
        System.out.println();
        voucherList.forEach(
                (k, v) -> {
                    printFixedAmountVoucher(k, v);
                    printPercentDiscountVoucher(k, v);
                }
        );
    }

    private void printFixedAmountVoucher(String k, Voucher v) {
        if (v instanceof FixedAmountVoucher) {
            System.out.println("[FixedAmountVoucher | Voucher ID] : " + k + " | discount amount : " + ((FixedAmountVoucher) v).getAmount());
        }
    }

    private void printPercentDiscountVoucher(String k, Voucher v) {
        if (v instanceof PercentDiscountVoucher) {
            System.out.println("[PercentDiscountVoucher | ID] : " + k + " | discount percent : " + ((PercentDiscountVoucher) v).getRate());
        }
    }

    @Override
    public void printListOfCustomer(List<Customer> customerList) {
        System.out.println();
        customerList.forEach(
                c -> System.out.println("[CustomerId] : " + c.getCustomerId() + " | name : " + c.getName() + " | email : " + c.getEmail())
        );
    }

    @Override
    public void printCustomer(Customer customer) {
        System.out.println();
        System.out.println("[CustomerId] : " + customer.getCustomerId() + " | name : " + customer.getName() + " | email : " + customer.getEmail());
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
