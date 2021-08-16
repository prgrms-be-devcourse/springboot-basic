package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.dto.VoucherRequestDto;
import org.prgrms.kdt.io.IoInteraction;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class CommandLineApplication {

    public static void main(String[] args) {
        // 입력을 받아서 수행하는 객체들이 있다.
        // IoC컨테이너를 이용해서 바우처관리를 위해 필요한 서비스와 리포를 빈으로 등록
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        // IoIntercation클래스는 다른클래스와 의존관계는 갖지않긴 하지만 객체 생성은 context에서 하도록함
        var io = applicationContext.getBean(IoInteraction.class);
        var voucherService = applicationContext.getBean(VoucherService.class);

        Scanner sc = new Scanner(System.in);

        // 첫 안내문 출력
        io.help();

        while (true) {

            // 입력
            String input = sc.next();

            switch (input) {
                case "exit":
                    io.exit();
                    System.exit(0);

                case "create":
                    io.create();
                    int num = sc.nextInt();
                    io.discountRate();
                    int discount = sc.nextInt();

                    // 사용자로부터 받은 바우처종류와 할인율을 저장해서 service단으로 옮길 dto객체
                    VoucherRequestDto requestDto = new VoucherRequestDto();
                    requestDto.setVoucherType(num);
                    requestDto.setDiscount(discount);

                    voucherService.saveVoucher(requestDto);
                    io.save();
                    break;

                case "list":
                    voucherService.getAllVouchers()
                            .forEach(System.out::println);
                    break;

                case "help":
                    io.help();
                    break;

                default:
                    io.fail(input);
                    break;
            }
        }
    }
}
