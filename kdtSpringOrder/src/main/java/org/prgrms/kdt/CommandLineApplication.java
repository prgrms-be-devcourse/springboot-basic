package org.prgrms.kdt;

import org.prgrms.kdt.controller.CommandType;
import org.prgrms.kdt.controller.InputController;
import org.prgrms.kdt.controller.OutputController;
import org.prgrms.kdt.dto.VoucherSaveRequestDto;
import org.prgrms.kdt.dto.VoucherType;
import org.prgrms.kdt.helper.MessageHelper;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

public class CommandLineApplication {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherProcess(applicationContext);

    }

    public static void voucherProcess(AnnotationConfigApplicationContext applicationContext) {

        InputController inputController = applicationContext.getBean(InputController.class);
        OutputController outputController = applicationContext.getBean(OutputController.class);
        MessageHelper messageHelper = applicationContext.getBean(MessageHelper.class);
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);

        while(true){
            messageHelper.showVoucherProgramGuideMessage();
            CommandType commandType = inputController.getCommandType();
            switch (commandType) {
                case EXIT -> {
                    runExit(messageHelper);
                }
                case CREATE -> {
                    runCreate(inputController, messageHelper, voucherService);
                    break;
                }
                case LIST -> {
                    runList(outputController, voucherService);
                    break;
                }
                case REPLAY -> {
                    runReplay(messageHelper);
                    break;
                }
                default -> {
                    runRetry(messageHelper);
                    break;
                }
            }
        }
    }

    public static void runExit(MessageHelper messageHelper) {
        messageHelper.showExitMessage();
        System.exit(0);
    }

    public static void runCreate(InputController inputController, MessageHelper messageHelper, VoucherService voucherService) {
        messageHelper.showVoucherSelectionMessage();
        VoucherType voucherType = inputController.getVoucherType();
        if(voucherType == VoucherType.UNDEFINED) {
            messageHelper.showRetryMessage();
            return;
        }

        messageHelper.showEnterVoucherDiscount();
        int rate = inputController.getDiscount();
        if(rate < 0) {
            messageHelper.showRetryMessage();
            return;
        }

        VoucherSaveRequestDto saveRequestDto = new VoucherSaveRequestDto(voucherType, rate);
        if(voucherService.isDuplicateVoucher(UUID.randomUUID())) {
            messageHelper.showDuplicateVoucherMessage();
            return;
        }

        voucherService.saveVoucher(saveRequestDto);
        messageHelper.showVoucherRegistrationSuccessMessage();
    }

    public static void runList(OutputController outputController, VoucherService voucherService) {
        outputController.showVoucherList(voucherService);
    }

    public static void runReplay(MessageHelper messageHelper) {
        messageHelper.showVoucherProgramGuideMessage();
    }

    public static void runRetry(MessageHelper messageHelper) {
        messageHelper.showRetryMessage();
    }

}