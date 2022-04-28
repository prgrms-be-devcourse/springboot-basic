package com.prgrms.kdt.springbootbasic;

import com.prgrms.kdt.springbootbasic.controller.VoucherController;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import com.prgrms.kdt.springbootbasic.inputPackage.CustomInput;
import com.prgrms.kdt.springbootbasic.outputPackage.CustomOutput;
import com.prgrms.kdt.springbootbasic.returnFormats.VoucherInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringbootBasicApplication {
	private static final Logger logger = LoggerFactory.getLogger(SpringbootBasicApplication.class);
	private static VoucherController voucherController;
	private static CustomOutput customOutput;
	private static CustomInput customInput;

	public static void main(String[] args) throws IOException {
		var applicationContext = SpringApplication.run(SpringbootBasicApplication.class, args);
		voucherController = applicationContext.getBean(VoucherController.class);
		customOutput = applicationContext.getBean(CustomOutput.class);
		customInput = applicationContext.getBean(CustomInput.class);
		//모든 메소드를 static으로 만들기 싫어서 생성자를 사용했는데, 더 좋은 방법이 있을까요?
		SpringbootBasicApplication springbootBasicApplication = new SpringbootBasicApplication();
		springbootBasicApplication.runVoucherProgramInOrder();
	}

	public void runVoucherProgramInOrder() throws IOException {
		String command = "";

		//일단 종료 상황을 명시는 했는데, handle Command 안에서 처리를 하고 싶어서 그렇게 해서 사용은 되지 않습니다....
		//이게 맞을까요? 의미는 없지만 명시를 하는게...?
		while(!command.equals("exit")) {
			logger.info("[SpringbootBasicApplication : runVoucherProgramInOrder] Start New Process");
			customOutput.informCommandWithConsole();
			command = customInput.getCommand();
			logger.info(MessageFormat.format("[SpringbootBasicApplication : runVoucherProgramInOrder] Command : {0}", command));
			handleCommand(command);
			customOutput.informProcessEnd();
			logger.info("[SpringbootBasicApplication : runVoucherProgramInOrder] End New Process");
		}
	}

	public void handleCommand(String command){
		CommandEnum.getCommandEnum(command).runCommand();
	}


	enum CommandEnum {
		CREATE("create") {
			@Override
			public void runCommand() {
				try{
					createVoucher();
				}catch(IOException e){
					logger.error("[SpringbootBasicApplication : handleCommand -> VoucherController.createVoucher] -> {}",e);
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
			return Arrays.stream(CommandEnum.values()).filter(c -> c.command.equals(command)).collect(Collectors.toList()).get(0);
		}

		public void createVoucher() throws IOException {
			customOutput.informNewVoucherInfo();
			VoucherInfo voucherInfo = customInput.getNewVoucherInfo();
			String voucherType = voucherInfo.getVoucherType();
			long discountAmount= voucherInfo.getDiscountAmount();

			voucherController.saveVoucher(voucherType, UUID.randomUUID(),discountAmount);
		}

		public void showVoucherList(){
			List<Voucher> voucherList = voucherController.getVoucherList();
			customOutput.printVoucherList(voucherList);
		}

		public abstract void runCommand();
	}

}
