package com.example.voucherproject.common.console;

import com.example.voucherproject.common.enums.*;
import com.example.voucherproject.user.enums.UserMenu;
import com.example.voucherproject.user.enums.UserType;
import com.example.voucherproject.voucher.enums.VoucherMenu;
import com.example.voucherproject.voucher.enums.VoucherType;
import com.example.voucherproject.wallet.enums.WalletMenu;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

import static com.example.voucherproject.common.enums.ServiceType.*;

@Slf4j
public class ConsoleIn implements Input {
    Scanner sc = new Scanner(System.in);

    @Override
    public UserMenu selectUserMenu(ServiceType serviceType) {
        serviceMenu(serviceType);
        System.out.print(">> ");
        String select = sc.nextLine().toUpperCase();
        try{
            return UserMenu.valueOf(select);
        }
        catch(Exception e){
            log.error(e.getMessage());
            System.out.println("잘못된 입력입니다.");
            return selectUserMenu(serviceType);
        }
    }

    @Override
    public VoucherMenu selectVoucherMenu(ServiceType serviceType) {
        serviceMenu(serviceType);
        System.out.print(">> ");
        String select = sc.nextLine().toUpperCase();
        try{
            return VoucherMenu.valueOf(select);
        }
        catch(Exception e){
            log.error(e.getMessage());
            System.out.println("잘못된 입력입니다.");
            return selectVoucherMenu(serviceType);
        }
    }

    @Override
    public VoucherType createVoucher() {
        System.out.println("=== Select Voucher Type ===");
        System.out.println("1. Fixed    Discount Voucher");
        System.out.println("2. Percent  Discount Voucher");
        System.out.print(">> ");
        try{
            return VoucherType.valueOf(sc.nextLine().toUpperCase());
        }
        catch(Exception e){
            log.error(e.getMessage());
            System.out.println("잘못된 입력입니다.");
            return createVoucher();
        }
    }

    @Override
    public String userName() {
        System.out.print("이름 >> ");
        return sc.nextLine();
    }

    @Override
    public UserType userType() {
        System.out.println("=== 유저 정보 불러오기 ===");
        System.out.println("1. NORMAL LIST");
        System.out.println("2. BLACK LIST");
        System.out.print(">> ");
        switch(sc.nextLine().charAt(0)){
            case '1':
            case 'n':
            case 'N':
                return UserType.NORMAL;
            case '2':
            case 'b':
            case 'B':
                return UserType.BLACK;
            default:
                System.out.println("잘못된 입력입니다.");
                return userType();
        }
    }

    @Override
    public ServiceType selectService() {
        System.out.println("=== All Program List ===");
        System.out.println("1. VOUCHER  Program");
        System.out.println("2. USER     Program");
        System.out.println("3. WALLET   Program");
        System.out.println("4. EXIT     Program");
        System.out.print("Select Number >> ");
        switch (sc.nextLine().charAt(0)){
            case '1':
            case 'v':
            case 'V':
                return VOUCHER_SERVICE;
            case '2':
            case 'u':
            case 'U':
                return USER_SERVICE;
            case '3':
            case 'w':
            case 'W':
                return WALLET_SERVICE;
            case '4':
            case 'e':
            case 'E':
                return EXIT;
            default:
                System.out.println("1 ~ 4 사이의 정수를 입력해 주세요\n");
                return selectService();
        }
    }

    @Override
    public Long amount() {
        System.out.print("할인정도를 입력해주세요 >> ");

        String amount = sc.nextLine();
        try{
            return Long.valueOf(amount);
        }
        catch(Exception e){
            log.error(e.getMessage());
            System.out.println("잘못된 입력입니다.");
            return amount();
        }
    }

    @Override
    public WalletMenu selectWalletMenu(ServiceType serviceType) {
        serviceMenu(serviceType);
        System.out.print(">> ");
        String select = sc.nextLine().toUpperCase();
        try{
            return WalletMenu.valueOf(select);
        }
        catch(Exception e){
            log.error(e.getMessage());
            System.out.println("잘못된 입력입니다.");
            return selectWalletMenu(serviceType);
        }
    }

    @Override
    public int selectUser(int size) {
        System.out.printf("유저를 선택해주세요 (1~%d)>> ",size);

        try{
            int idx = Integer.parseInt(sc.nextLine());
            System.out.println();
            if (idx < 1 || idx > size) {
                System.out.printf("(1~%d) 범위로 다시 입력해주세요\n",size);
                return selectUser(size);
            }
            return idx-1;
        }
        catch(Exception e){
            log.info(e.getMessage());
            System.out.println("유효하지 않은 입력입니다.");
            return selectUser(size);
        }
    }

    @Override
    public int selectVoucher(int size) {
        System.out.printf("바우처를 선택해주세요 (1~%d)>> ",size);
        try{
            int idx = Integer.parseInt(sc.nextLine());
            System.out.println();
            if (idx < 1 || idx > size) {
                System.out.printf("(1~%d) 범위로 다시 입력해주세요\n",size);
                return selectVoucher(size);
            }
            return idx-1;
        }
        catch(Exception e){
            log.info(e.getMessage());
            System.out.println("유효하지 않은 입력입니다.");
            return selectVoucher(size);
        }
    }

    @Override
    public UserType isBlacklist() {
        System.out.print("해당 유저를 블랙리스트에 추가하시겠습니까? (y/n) >> ");
        switch(sc.nextLine().charAt(0)){
            case 'y':
            case 'Y':
                return UserType.BLACK;
            case 'n':
            case 'N':
                return UserType.NORMAL;
            default:
                System.out.println("Y(y) 또는 N(n) 을 입력해주세요");
                return isBlacklist();
        }
    }

    private void serviceMenu(ServiceType type){
        switch (type){
            case USER_SERVICE:
                System.out.println("=== User Program ===");
                System.out.println("`create`  "+" to create a new user");
                System.out.println("`list`    "+" to list   all users");
                System.out.println("`vouchers`"+" to list   all users");
                System.out.println("`home`    "+" to exit   the user program");
                break;
            case VOUCHER_SERVICE:
                System.out.println("=== Voucher Program ===");
                System.out.println("`create` "+" to create a new voucher");
                System.out.println("`list`   "+" to list   all vouchers");
                System.out.println("`users`  "+" to list   all vouchers");
                System.out.println("`home`   "+" to exit   the voucher program");
                break;
            case WALLET_SERVICE:
                System.out.println("=== Wallet Program ===");
                System.out.println("`Assign` "+" to create a new wallet");
                System.out.println("`Delete` "+" to delete a exist wallet");
                System.out.println("`List`   "+" to list all wallets");
                System.out.println("`home`   "+" to exit   the voucher program");
                break;
            default:
                log.error("존재하지 않는 서비스 입니다");
                break;
        }
    }
}
