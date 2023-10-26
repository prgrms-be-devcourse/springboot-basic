package org.prgrms.prgrmsspring.domain;

import lombok.Getter;
import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.controller.CustomerController;
import org.prgrms.prgrmsspring.controller.VoucherController;
import org.prgrms.prgrmsspring.controller.WalletController;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.exception.NotFoundException;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.function.Function;

public enum FunctionType {
    EXIT(0, null),
    CUSTOMER(1, applicationContext -> applicationContext.getBean(CustomerController.class)),
    VOUCHER(2, applicationContext -> applicationContext.getBean(VoucherController.class)),
    WALLET(3, applicationContext -> applicationContext.getBean(WalletController.class));

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
