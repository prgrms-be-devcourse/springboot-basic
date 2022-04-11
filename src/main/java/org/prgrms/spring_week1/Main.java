package org.prgrms.spring_week1;

import org.prgrms.spring_week1.models.Order;
import org.prgrms.spring_week1.models.OrderItem;
import org.prgrms.spring_week1.models.Voucher;
import org.prgrms.spring_week1.repositories.VoucherMemoryRepository;
import org.prgrms.spring_week1.repositories.VoucherRepository;
import org.prgrms.spring_week1.services.OrderService;
import org.prgrms.spring_week1.services.VoucherService;
import org.prgrms.spring_week1.services.io.ConsoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.System.exit;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class); // 모든 인스턴스가 공유(static) 변경불가(final)

    public static void main(String[] args) {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
        ac.register(AppConfig.class);


        // profile test
        ConfigurableEnvironment environment = ac.getEnvironment();
        environment.setActiveProfiles("local");

        ac.refresh();

        VoucherService vs = ac.getBean(VoucherService.class);
        OrderService os = ac.getBean(OrderService.class);
        ConsoleService cs = ac.getBean(ConsoleService.class);


        while(true){
            cs.mainMenu();
            String opt = cs.input();

            if(opt.equals("create")){ // 생성
                cs.voucherCreate();
                String type = cs.input();

                if(type.equals("1")){ // fixedVoucher
                    cs.output("정해진 할인가격을 입력하세요.");
                    String amount = cs.input();
                    System.out.println(amount);
                    vs.createFixedVoucher(Long.parseLong(amount));


                } else if(type.equals("2")){ // percentVoucher
                    cs.output("할인율을 입력하세요");
                    String percent = cs.input();
                    vs.createPercentVoucher(Long.parseLong(percent));

                } else { // 잘못된 입력
                    cs.wrongInput();
                }

            } else if(opt.equals("list")){  // 조회
                System.out.println(vs.getAllVoucher());

            } else if(opt.equals("exit")){  // 종료
                exit(0);
            } else{ // 잘못된 입력
                cs.wrongInput();
            }
        }

    }
}
