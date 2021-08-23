package org.prgrms.kdt;

import org.prgrms.kdt.controller.InputController;
import org.prgrms.kdt.controller.OutputController;
import org.prgrms.kdt.dto.VoucherSaveRequestDto;
import org.prgrms.kdt.enums.CommandType;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.service.CustomerService;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.prgrms.kdt.helper.MessageHelper.*;

@SpringBootApplication
public class KdtApplication {

    private final static Logger logger = LoggerFactory.getLogger(KdtApplication.class);

	public static void main(String[] args) {

        logger.info("voucher Process started.");

        var springApplication = new SpringApplication(KdtApplication.class);
		springApplication.setAdditionalProfiles("prod");
		ConfigurableApplicationContext applicationContext = springApplication.run(args);

        voucherProcess(applicationContext);
	}

    public static void voucherProcess(ConfigurableApplicationContext applicationContext) {

        InputController inputController = applicationContext.getBean(InputController.class);
        OutputController outputController = applicationContext.getBean(OutputController.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);

        while(true){
            showVoucherProgramGuideMessage();
            CommandType commandType = inputController.getCommandType();

            logger.info("CommandType is {}", commandType.toString());

            switch (commandType) {
                case EXIT -> {
                    runExit();
                }
                case CREATE -> {
                    runCreate(inputController, voucherService);
                }
                case LIST -> {
                    runList(outputController, voucherService);
                }
                case BLACKLIST -> {
                    runBadCustomerList(outputController, customerService);
                }
                case REPLAY -> {
                    runReplay();
                }
                default -> {
                    runRetry();
                }
            }
        }
    }

    public static void runExit() {
        logger.info("Starts runExit()");
        showExitMessage();
        logger.info("Finished runExit()");
        System.exit(0);
    }

    public static void runCreate(InputController inputController, VoucherService voucherService) {
        logger.info("Starts runCreate()");
        showVoucherSelectionMessage();
        VoucherType type = inputController.getVoucherType();
        showEnterVoucherDiscount();
        long discount = inputController.getDiscount();
        voucherService.createVoucher(new VoucherSaveRequestDto(type, discount));
        showVoucherRegistrationSuccessMessage();
        logger.info("Finished runCreate()");
    }

    public static void runList(OutputController outputController, VoucherService voucherService) {
        logger.info("Starts runList()");
        outputController.showVoucherList(voucherService.getAllVouchers());
        logger.info("Finished runList()");
    }

    public static void runBadCustomerList(OutputController outputController, CustomerService customerService) {
        logger.info("Starts runBadCustomerList()");
        outputController.showBadCustomerList(customerService.getAllVouchers());
        logger.info("Finished runBadCustomerList()");
    }

    public static void runReplay() {
        logger.info("Starts runReplay()");
        showVoucherProgramGuideMessage();
        logger.info("Finished runReplay()");
    }

    public static void runRetry() {
        logger.info("Starts runRetry()");
        showRetryMessage();
        logger.info("Finished runRetry()");
    }

}