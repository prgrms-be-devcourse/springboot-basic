package com.prgms.vouchermanager.util.io;

import com.prgms.vouchermanager.domain.customer.Customer;
import com.prgms.vouchermanager.domain.wallet.Wallet;
import org.springframework.stereotype.Component;

@Component
public class ConsoleOutput {

    private void printCommon(String s) {
        System.out.println(s);
    }
    public void printFrontMenu() {
        printCommon("""
                
                사용할 메뉴를 선택해주세요.
                
                1. Voucher
                
                2. Customer
                
                3. Wallet
                
                4. EXIT
                """);
    }

    public void printVoucherMenu() {
        printCommon("""
                === Voucher Program ===
                
                이용할 바우처 메뉴를 선택해주세요
                
                1. create
                
                2. update
                
                3. list
                
                4. find one
                
                5. delete one
                
                6. delete all
                
                """);
    }


    public void printVoucherType() {
        printCommon("""
                                
                이용하실 쿠폰의 타입 번호를 입력해주세요
                                
                1. FixedAmount
                
                2. PercentDiscount
                                
                """);
    }

    public void printCustomerMenu() {
        printCommon("""
                
                이용하실 고객 메뉴를 입력해주세요
                
                1. CREATE
                
                2. UPDATE
                
                3. FIND ALL
                
                4. FIND ONE
                
                5. DELETE ONE
                
                6. DELETE ALL
                
                7. BLACK LIST
                
                """);
    }

    public void printWalletMenu() {
        printCommon("""
                이용하실 지갑 메뉴를 입력해주세요
                
                1. CREATE
                
                2. FIND BY CUSTOMER ID
                
                3. FIND BY VOUCHER ID
                
                4. DELETE BY CUSTOMER ID
                
                """);
    }
    public void printVoucherAmount() {
        printCommon("쿠폰의 할인값을 입력해주세요");
    }

    public void printVoucherId() {
        printCommon("쿠폰 번호를 입력해주세요.");
    }

    public void printSuccessDelete() {
        printCommon("성공적으로 삭제되었습니다.");
    }

    public void printCustomerName() {
        printCommon("고객명을 입력해주세요.{");
    }

    public void printCustomerEmail() {
        printCommon("이메일을 입력해주세요.");
    }

    public void printBlackList() {
        printCommon("블랙리스트에 등록하시려면 1번, 아니라면 2번을 입력해주세요");
    }

    public void printCustomerId() {
        printCommon("고객 ID를 입력해주세요.");
    }

    public void printCustomer(Customer customer) {
        printCommon(customer.toString());
    }

    public void printSuccessUpdate() {
        printCommon("성공적으로 업데이트 되었습니다.");
    }

    public void printWallet(Wallet wallet) {
        printCommon(wallet.toString());
    }
}
