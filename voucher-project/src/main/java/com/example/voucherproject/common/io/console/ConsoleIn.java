package com.example.voucherproject.common.io.console;

import com.example.voucherproject.common.enums.MenuType;
import com.example.voucherproject.common.enums.ServiceType;
import com.example.voucherproject.common.enums.UserType;
import com.example.voucherproject.common.enums.VoucherType;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

import static com.example.voucherproject.common.enums.ServiceType.*;

@Slf4j
public class ConsoleIn implements Input {
    Scanner sc = new Scanner(System.in);

    @Override
    public MenuType selectMenu(ServiceType serviceType) {
        serviceMenu(serviceType);
        System.out.print(">> ");
        String select = sc.nextLine().toUpperCase();
        try{
            return MenuType.valueOf(select);
        }
        catch(Exception e){
            log.error(e.getMessage());
            System.out.println("잘못된 입력입니다.");
            return selectMenu(serviceType);
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
        String name = sc.nextLine();
        return name;
    }

    @Override
    public UserType userType() {
        System.out.println("=== 유저 정보 불러오기 ===");
        System.out.println("1. ALL LIST");
        System.out.println("2. BLACK LIST");
        System.out.print(">> ");
        switch(sc.nextLine().charAt(0)){
            case '1':
            case 'a':
            case 'A':
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
        System.out.println("3. EXIT     Program");
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
            case 'e':
            case 'E':
                return EXIT;
            default:
                System.out.println("1 ~ 3 사이의 정수를 입력해 주세요\n");
                return selectService();
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
                System.out.println("create "+" to create a new user");
                System.out.println("list   "+" to list   all users");
                System.out.println("home   "+" to exit   the user program");
                break;
            case VOUCHER_SERVICE:
                System.out.println("=== Voucher Program ===");
                System.out.println("create "+" to create a new voucher");
                System.out.println("list   "+" to list   all voucher");
                System.out.println("home   "+" to exit   the voucher program");
                break;
            default:
                log.error("존재하지 않는 서비스 입니다");
                break;
        }
    }
}
