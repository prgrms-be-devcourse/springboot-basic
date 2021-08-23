package org.prgrms.kdt.Voucher;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.Customers.CustomersService;
import org.prgrms.kdt.Voucher.TypeStatus;
import org.prgrms.kdt.Voucher.VoucherRequest;
import org.prgrms.kdt.Voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.MessageFormat;
import java.util.Scanner;
import java.util.UUID;

public class VoucherApplication {
    TypeStatus type=TypeStatus.Fixed;
    public static void main(String[] args) {

        //자바 기반의 메타데이터 설정은 어노테이션
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfiguration.class);
        var environment=applicationContext.getEnvironment();
        environment.setActiveProfiles("prod");
//        //BeanFactory 의 초기화가 이 부분에서 진행되고, Bean 이 등록되는 과정 등이 포함되어 있다.
//        //내가 이해한 바로는 프로파일을 설정해준후 빈을 등록하는 과정이 필요해서 사용하는 것 같다.
        applicationContext.refresh();

        var voucherService = applicationContext.getBean(VoucherService.class);
        var customersService=applicationContext.getBean(CustomersService.class);




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

                    if(voucherService.findAll().isEmpty()) System.out.println("저장된 바우처가 없습니다.");
                    else {
                        System.out.println("바우처 목록");
                        voucherService.findAll().forEach(voucher -> System.out.println(MessageFormat.format("{0},{1},{2}", voucher.getVoucherId(), voucher.getType(), voucher.getVoucherdiscount())));
                    }}
                case  "blacklist"->{

                    //질문
                    if ( customersService.findAll().isEmpty()) System.out.println("블랙리스트가 없습니다");
                    else{
                        System.out.println("블랙리스트 목록");
                        customersService.findAll().forEach(customers -> System.out.println(MessageFormat.format("{0}.{1} {2}",customers.getNum(),customers.getId(),customers.getName())));
                    }
                }
                case "exit" -> System.out.println("종료되었습니다");
                default -> System.out.println("다시 입력해 주세요");
            }
        }
    }


}
