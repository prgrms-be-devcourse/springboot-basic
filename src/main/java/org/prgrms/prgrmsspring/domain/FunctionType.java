package org.prgrms.prgrmsspring.domain;

import lombok.Getter;
import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.controller.CustomerController;
import org.prgrms.prgrmsspring.controller.VoucherController;
import org.prgrms.prgrmsspring.controller.WalletController;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.exception.NotFoundException;
import org.prgrms.prgrmsspring.service.CustomerService;
import org.prgrms.prgrmsspring.service.VoucherService;
import org.prgrms.prgrmsspring.service.WalletService;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.function.Function;

public enum FunctionType {
    EXIT(0, null),
    CUSTOMER(1, applicationContext -> {
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        CommandLineView commandLineView = applicationContext.getBean(CommandLineView.class);
        return new CustomerController(commandLineView, customerService);
    }),
    VOUCHER(2, applicationContext -> {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CommandLineView commandLineView = applicationContext.getBean(CommandLineView.class);
        return new VoucherController(commandLineView, voucherService);
    }),
    WALLET(3, applicationContext -> {
        WalletService walletService = applicationContext.getBean(WalletService.class);
        CommandLineView commandLineView = applicationContext.getBean(CommandLineView.class);
        return new WalletController(commandLineView, walletService);
    });

    @Getter
    private final int modeNumber;
    private final Function<ApplicationContext, ApplicationController> controllerInstantiator;

    FunctionType(int modeNumber, Function<ApplicationContext, ApplicationController> controllerInstantiator) {
        this.modeNumber = modeNumber;
        this.controllerInstantiator = controllerInstantiator;
    }

    public ApplicationController createController(ApplicationContext applicationContext) {
        return controllerInstantiator.apply(applicationContext);
    }

    public static FunctionType from(int modeNumber) {
        return Arrays.stream(FunctionType.values())
                .filter(c -> c.modeNumber == modeNumber)
                .findAny()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_CONTROLLER_TYPE.getMessage()));
    }

    public static boolean isExit(FunctionType functionType) {
        return EXIT == functionType;
    }
}
