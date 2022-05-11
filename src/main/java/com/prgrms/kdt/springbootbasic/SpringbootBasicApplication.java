package com.prgrms.kdt.springbootbasic;

import com.prgrms.kdt.springbootbasic.voucher.controller.ConsoleVoucherController;
import com.prgrms.kdt.springbootbasic.voucher.entity.Voucher;
import com.prgrms.kdt.springbootbasic.exception.NotSupportedCommandException;
import com.prgrms.kdt.springbootbasic.io.inputPackage.CustomInput;
import com.prgrms.kdt.springbootbasic.io.outputPackage.CustomOutput;
import com.prgrms.kdt.springbootbasic.returnFormats.VoucherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class SpringbootBasicApplication {
	private static final Logger logger = LoggerFactory.getLogger(SpringbootBasicApplication.class);
	private static ConsoleVoucherController consoleVoucherController;
	private static CustomOutput customOutput;
	private static CustomInput customInput;

	public static void main(String[] args) throws IOException {
		var applicationContext = SpringApplication.run(SpringbootBasicApplication.class, args);
		consoleVoucherController = applicationContext.getBean(ConsoleVoucherController.class);
		customOutput = applicationContext.getBean(CustomOutput.class);
		customInput = applicationContext.getBean(CustomInput.class);
		//모든 메소드를 static으로 만들기 싫어서 생성자를 사용했는데, 더 좋은 방법이 있을까요?
		SpringbootBasicApplication springbootBasicApplication = new SpringbootBasicApplication();
		springbootBasicApplication.runVoucherProgramInOrder();
	}

	public void runVoucherProgramInOrder() throws IOException {
		String command = "";

		while(!command.equals("exit")) {
			logger.info("[SpringbootBasicApplication : runVoucherProgramInOrder] Start New Process");
			customOutput.informCommandWithConsole();
			var inputCommand = customInput.getCommand();
			if (inputCommand.isEmpty()){
				customOutput.wrongInput();
				continue;
			}
			command = inputCommand.get();
			logger.info("[SpringbootBasicApplication : runVoucherProgramInOrder] Command : {}", command);
			if (command.equals("exit")){
				break;
			}
			handleCommand(command);
			customOutput.informProcessEnd();
			logger.info("[SpringbootBasicApplication : runVoucherProgramInOrder] End New Process");
		}
	}

	public void handleCommand(String command){
		var commandObject = CommandEnum.getCommandEnum(command);
		if (commandObject != null)
			commandObject.runCommand();
	}


	enum CommandEnum {
		CREATE("create") {
			@Override
			public void runCommand() {
				try{
					createVoucher();
				}catch(IOException e){
					logger.error("[SpringbootBasicApplication : handleCommand -> VoucherController.createVoucher] -> {}",e.getMessage());
				}
			}
		},
		LIST("list") {
			@Override
			public void runCommand() {
				showVoucherList();
			}
		},
		EXIT("exit"){
			@Override
			public void runCommand(){
				System.exit(0);
			}
		};

		private final String command;

		CommandEnum(String command) {
			this.command = command;
		}

		public static CommandEnum getCommandEnum(String command){
			return Arrays.stream(CommandEnum.values())
					.filter(c -> c.command.equals(command))
					.findAny()
					.orElseThrow(() -> new NotSupportedCommandException("제공하지 않는 명령어입니다. : {}", command));
		}

		public void createVoucher() throws IOException {
			customOutput.informNewVoucherInfo();
			var newVoucherInfo = customInput.getNewVoucherInfo();
			if (newVoucherInfo.isEmpty()){
				customOutput.errorOccurred();
				return;
			}
			VoucherInfo voucherInfo = newVoucherInfo.get();
			String voucherType = voucherInfo.getVoucherType();
			long discountAmount= voucherInfo.getDiscountAmount();

			consoleVoucherController.saveVoucher(voucherType, UUID.randomUUID(),discountAmount);
		}

		public void showVoucherList(){
			List<Voucher> voucherList = consoleVoucherController.getVoucherList();
			customOutput.printVoucherList(voucherList);
		}

		public abstract void runCommand();
	}

}
