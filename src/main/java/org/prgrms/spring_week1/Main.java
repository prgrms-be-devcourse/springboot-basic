package org.prgrms.spring_week1;

import org.prgrms.spring_week1.models.Order;
import org.prgrms.spring_week1.models.OrderItem;
import org.prgrms.spring_week1.models.Voucher;
import org.prgrms.spring_week1.services.OrderService;
import org.prgrms.spring_week1.services.VoucherService;
import org.prgrms.spring_week1.services.io.ConsoleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

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


            } else if(opt.equals("exit")){  // 종료
                exit(0);
            } else{ // 잘못된 입력
                cs.wrongInput();
            }
        }

    }
}
