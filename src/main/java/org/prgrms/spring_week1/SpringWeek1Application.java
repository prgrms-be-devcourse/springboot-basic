package org.prgrms.spring_week1;

import static java.lang.System.exit;

import org.prgrms.spring_week1.Order.OrderService;
import org.prgrms.spring_week1.Voucher.VoucherService;
import org.prgrms.spring_week1.io.ConsoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class SpringWeek1Application {

    private static final Logger logger = LoggerFactory.getLogger(SpringWeek1Application.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext ac = SpringApplication
            .run(SpringWeek1Application.class, args);

        VoucherService voucherService = ac.getBean(VoucherService.class);
        OrderService orderService = ac.getBean(OrderService.class);
        ConsoleService consoleService = ac.getBean(ConsoleService.class);

        while (true) {
            consoleService.mainMenu();
            String opt = consoleService.input();

            if (opt.equals("create")) { // 생성
                consoleService.voucherCreate();
                String type = consoleService.input();

                if (type.equals("1")) { // fixedVoucher
                    consoleService.output("정해진 할인가격을 입력하세요.");
                    String amount = consoleService.input();
                    voucherService.createFixedVoucher(Long.parseLong(amount));


                } else if (type.equals("2")) { // percentVoucher
                    consoleService.output("할인율을 입력하세요");
                    String percent = consoleService.input();
                    voucherService.createPercentVoucher(Long.parseLong(percent));

                } else { // 잘못된 입력
                    logger.info("사용자가 생성할바우처 종류선택에서 기대되지않는 입력을 했습니다.");
                    consoleService.wrongInput();
                }

            } else if (opt.equals("list")) {  // 조회
                for (String voucher : voucherService.getAllVoucher()) {
                    consoleService.output(voucher);
                }

            } else if (opt.equals("exit")) {  // 종료
                exit(0);
            } else { // 잘못된 입력
                logger.info("사용자가 최상위 메뉴선택에서 기대되지않는 입력을 했습니다.");
                consoleService.wrongInput();
            }
        }

    }


}
