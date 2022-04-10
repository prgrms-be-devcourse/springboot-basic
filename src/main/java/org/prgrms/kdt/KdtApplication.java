package org.prgrms.kdt;

import org.prgrms.kdt.domain.command.types.CommandType;
import org.prgrms.kdt.domain.member.model.Member;
import org.prgrms.kdt.domain.member.repository.FileMemberRepository;
import org.prgrms.kdt.domain.voucher.controller.VoucherController;
import org.prgrms.kdt.domain.voucher.service.VoucherService;
import org.prgrms.kdt.util.Console;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class KdtApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KdtApplication.class, args);
		VoucherController voucherController = context.getBean(VoucherController.class);
		FileMemberRepository memberRepository = context.getBean(FileMemberRepository.class);
		Logger logger = LoggerFactory.getLogger(VoucherService.class);
		CommandType commandType;

		List<Member> blackListMembers = memberRepository.findAll();
		logger.info("Read Black List Members: {}", blackListMembers);
		do {
			String commandInput = Console.inputCommand();
			commandType = CommandType.findCommand(commandInput);
			voucherController.processCommand(commandType);
		} while (commandType != CommandType.EXIT);
	}
}
