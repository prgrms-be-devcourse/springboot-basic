package com.programmers.springbootbasic.service;

import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.domain.VoucherType;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class VoucherManager implements Runnable {

    private final Scanner sc = new Scanner(System.in);

    private VoucherService voucherService;

    public VoucherManager(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @Override
    public void run() {
        while(true) {
            showMenu();

            String input = sc.next();

            try {
                if (input.equals("1")) {
                    System.out.print("할인권의 종류를 선택하세요.(1.고정 금액 2.고정 비율) ==> ");
                    String choice = sc.next();

                    if (choice.equals("1"))
                        voucherService.createVoucher(VoucherType.FIXED_AMOUNT);
                    else if (choice.equals("2"))
                        voucherService.createVoucher(VoucherType.PERCENT_DISCOUNT);
                    else
                        System.out.println("잘못된 입력입니다. 처음 메뉴로 돌아갑니다.");
                }
                else if (input.equals("2")) {
                    System.out.print("검색할 할인권 ID를 입력하세요 ==> ");
                    String voucherId = sc.next();
                    searchVoucher(voucherId);
                }
                else if (input.equals("3")) {
                    List<Voucher> vouchers = voucherService.findVouchers();
                    listVouchers(vouchers);
                }
                else if (input.equals("4")) {
                    break;
                }
                else {
                    System.out.println("메뉴를 다시 선택해 주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 알맞은 수를 입력하세요.");
            } catch(IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } finally {
                System.out.println();
            }
        }
    }

    public static void showMenu () {
        System.out.println("******** 할인권 관리 프로그램입니다. ******** ");
        System.out.println("아래의 메뉴 중 하나를 선택하세요.\n");
        System.out.println("1. 새로운 할인권 생성");
        System.out.println("2. 할인권 검색");
        System.out.println("3. 생성된 할인권 리스트 보기");
        System.out.println("4. 프로그램 종료");
        System.out.print(" ==> ");
    }

    private void searchVoucher(String voucherId) {
        Optional<Voucher> op = voucherService.findVoucher(voucherId);

        if (op.isPresent())
            System.out.println(op.get());
        else
            System.out.println("해당하는 할인권이 존재하지 않습니다.");
    }

    private void listVouchers(List<Voucher> vouchers) {
        if (vouchers.size() == 0)
            System.out.println("생성된 할인권이 없습니다.");
        else
            vouchers.stream().forEach(System.out::println);
    }

}
