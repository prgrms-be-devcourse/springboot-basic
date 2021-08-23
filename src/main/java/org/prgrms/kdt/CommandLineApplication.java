package org.prgrms.kdt;

import org.prgrms.kdt.BlackCustomer.BlackCustomerService;
import org.prgrms.kdt.dto.VoucherRequestDto;
import org.prgrms.kdt.io.Command;
import org.prgrms.kdt.io.IoInteraction;
import org.prgrms.kdt.voucher.service.VoucherService;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class CommandLineApplication {

    public static void main(String[] args) {

        // IoC컨테이너를 이용해서 바우처관리를 위해 필요한 서비스와 리포를 빈으로 등록
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        // IoIntercation클래스는 다른클래스와 의존관계는 갖지않긴 하지만 객체 생성은 context에서 하도록함
        var io = applicationContext.getBean(IoInteraction.class);
        var voucherService = applicationContext.getBean(VoucherService.class);
        var customerService = applicationContext.getBean(BlackCustomerService.class);

        Scanner sc = new Scanner(System.in);

        // 첫 안내문 출력
        io.help();

        while (true) {

            // 입력
            String input = sc.next();

            var command = findCommand(input);

            switch (command) {
                case EXIT:
                    io.exit();
                    System.exit(0);

                case CREATE:
                    io.create();
                    int num = sc.nextInt();
                    io.discountRate();
                    int discount = sc.nextInt();

                    // 입력받은 숫자로 voucherType가져오기
                    var voucherType = findVoucherType(num);

                    // 사용자로부터 받은 바우처종류와 할인율을 저장해서 service단으로 옮길 dto객체
                    VoucherRequestDto requestDto = new VoucherRequestDto();
                    requestDto.setVoucherType(voucherType);
                    requestDto.setDiscount(discount);

                    voucherService.saveVoucher(requestDto);
                    io.save();
                    break;

                case LIST:
                    var voucherList = voucherService.getAllVouchers();
                    io.list(voucherList);
                    break;

                case BLACK:
                    var customerList = customerService.getAllBlackList();
                    io.black(customerList);
                    break;

                case HELP:
                    io.help();
                    break;

                default:
                    io.fail(input);
                    break;
            }
        }
    }

    public static Command findCommand(String input) {
        for (Command c : Command.values()) {
            if (c.getCommand().equals(input)) {
                return c;
            }
        }
        return Command.FAIL;
    }

    public static VoucherType findVoucherType(int input) {
        for (VoucherType voucherType : VoucherType.values()) {
            if (voucherType.getIdx()==input) {
                return voucherType;
            }
        }
        return VoucherType.FAIL;
    }
}
