package com.programmers.commandLine;

import com.programmers.commandLine.domain.voucher.application.VoucherApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 *
 *  CommandLindApplication의 설명을 여기에 작성한다.
 *  CommandLindApplication의 시작 부분입니다.
 *
 *  @author 장주영
 *  @version 1.0.0
 *  작성일 2022/11/02
 *
**/
@SpringBootApplication
public class CommandLindApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(CommandLindApplication.class);
		applicationContext.getBean(VoucherApplication.class).run();
	}
}
