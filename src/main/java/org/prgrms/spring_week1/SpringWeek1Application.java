package org.prgrms.spring_week1;

import static java.lang.System.exit;

import org.prgrms.spring_week1.services.OrderService;
import org.prgrms.spring_week1.services.VoucherService;
import org.prgrms.spring_week1.services.io.ConsoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class SpringWeek1Application {

	private static final Logger logger = LoggerFactory.getLogger(SpringWeek1Application.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(SpringWeek1Application.class, args);
		AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);


		VoucherService vs = ac.getBean(VoucherService.class);
		OrderService os = ac.getBean(OrderService.class);
		ConsoleService cs = ac.getBean(ConsoleService.class);

		logger.info("complete bean setting");

		while(true){
			cs.mainMenu();
			String opt = cs.input();

			if(opt.equals("create")){ // 생성
				cs.voucherCreate();
				String type = cs.input();

				if(type.equals("1")){ // fixedVoucher
					cs.output("정해진 할인가격을 입력하세요.");
					String amount = cs.input();
					vs.createFixedVoucher(Long.parseLong(amount));


				} else if(type.equals("2")){ // percentVoucher
					cs.output("할인율을 입력하세요");
					String percent = cs.input();
					vs.createPercentVoucher(Long.parseLong(percent));

				} else { // 잘못된 입력
					cs.wrongInput();
				}

			} else if(opt.equals("list")){  // 조회
				for(String voucher : vs.getAllVoucher()){
					cs.output(voucher);
				}

			} else if(opt.equals("exit")){  // 종료
				exit(0);
			} else{ // 잘못된 입력
				cs.wrongInput();
			}
		}

	}


}
