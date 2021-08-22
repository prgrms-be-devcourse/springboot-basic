package org.prgrms.kdt;

import org.prgrms.kdt.Model.Voucher;
import org.prgrms.kdt.Model.VoucherRequest;
import org.prgrms.kdt.Service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class VoucherApplication {
    TypeStatus type=TypeStatus.Fixed;
    public static void main(String[] args) {

        //자바 기반의 메타데이터 설정은 어노테이션
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var voucherService = applicationContext.getBean(VoucherService.class);




        while (true) {
            System.out.println("=== Voucher Program ===");
            Scanner scanner = new Scanner(System.in);
            String cmd = scanner.nextLine();
            switch (cmd) {
                case "create" -> {
                    System.out.println("만들기 원하는 바우처의 종류를 선택하세요 1. 할인율 2. 할인가격");
                    int type_v = scanner.nextInt();
                    if (type_v == 1) {
                        System.out.println("할인율을 설정해 주세요.");
                        int percent = scanner.nextInt();
                        var voucherId = UUID.randomUUID();
                        VoucherRequest voucherRequest = new VoucherRequest(voucherId, percent);
                        voucherService.createVoucher(voucherId, TypeStatus.Percent, voucherRequest);

                    } else {
                        System.out.println("할인가격을 설정해 주세요.");
                        int amount = scanner.nextInt();
                        var voucherId = UUID.randomUUID();
                        VoucherRequest voucherRequest = new VoucherRequest(voucherId, amount);
                        voucherService.createVoucher(voucherId, TypeStatus.Fixed, voucherRequest);

                    }
                    System.out.println("바우처 저장이 완료 되었습니다");
                }
                case "list" -> {
                    System.out.println("바우처 목록");
                    voucherService.findAll().forEach(voucher -> System.out.println(voucher.getVoucherId() + "," + voucher.getVoucherdiscount() + "," + voucher.getType()));
                }
                case "exit" -> System.out.println("종료되었습니다");
                default -> System.out.println("다시 입력해 주세요");
            }
        }
    }


}
