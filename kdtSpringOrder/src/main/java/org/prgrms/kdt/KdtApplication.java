package org.prgrms.kdt;

import org.prgrms.kdt.controller.InputController;
import org.prgrms.kdt.controller.OutputController;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.voucher.Voucher;
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

import java.util.List;
import java.util.UUID;

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
                case ALLVOUCHERLIST -> {
                    runAllVoucherList(outputController, voucherService.getAllVouchers());
                }
                case VOUCHERLISTBYCUSTOMERID -> {
                    runVoucherListByCustomerId(inputController, outputController, voucherService);
                }
                case VOUCHERBYVOUCHERID -> {
                    runVoucherByVoucherId(inputController, outputController, voucherService);
                }
                case DELETEVOUCHER -> {
                    runDeleteVoucher(inputController, voucherService);
                }
                case BLACKLIST -> {
                    runBadCustomerList(outputController, customerService.getAllBadCustomer());
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
        showEnterCustomerIdMessage();
        UUID customerId = inputController.getCustomerId();
        showVoucherSelectionMessage();
        VoucherType type = inputController.getVoucherType();
        showEnterVoucherDiscount();
        long discount = inputController.getDiscount();
        voucherService.createVoucher(new VoucherSaveRequestDto(customerId, type, discount));
        showVoucherRegistrationSuccessMessage();
        logger.info("Finished runCreate()");
    }

    public static void runAllVoucherList(OutputController outputController, List<Voucher> voucherList) {
        logger.info("Starts runAllVoucherList()");
        outputController.showVoucherList(voucherList);
        logger.info("Finished runAllVoucherList()");
    }

    public static void runVoucherListByCustomerId(InputController inputController, OutputController outputController, VoucherService voucherService) {
        logger.info("Starts runVoucherListByCustomerId()");
        showEnterCustomerIdMessage();
        UUID customerId = inputController.getCustomerId();
        outputController.showVoucherList(voucherService.getVouchersByCustomerId(customerId));
        logger.info("Finished runVoucherListByCustomerId()");
    }

    public static void runVoucherByVoucherId(InputController inputController, OutputController outputController, VoucherService voucherService) {
        logger.info("Starts runVoucherByVoucherId()");
        showEnterVoucherIdMessgae();
        UUID voucherId = inputController.getVoucherId();
        outputController.showVoucherList(List.of(voucherService.getVoucher(voucherId)));
        logger.info("Finished runVoucherByVoucherId()");

    }

    public static void runDeleteVoucher(InputController inputController, VoucherService voucherService) {
        logger.info("Starts runVoucherByVoucherId()");
        showEnterDeleteVoucherInfo();
        UUID customerId = inputController.getCustomerId();
        UUID voucherId = inputController.getVoucherId();
        voucherService.deleteVoucher(customerId, voucherId);
        logger.info("Finished runVoucherByVoucherId()");
    }

    public static void runBadCustomerList(OutputController outputController, List<Customer> customerVoucherList) {
        logger.info("Starts runBadCustomerList()");
        outputController.showCustomerVoucherList(customerVoucherList);
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