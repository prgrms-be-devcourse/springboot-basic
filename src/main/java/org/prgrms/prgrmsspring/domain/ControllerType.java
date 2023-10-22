package org.prgrms.prgrmsspring.domain;

import org.prgrms.prgrmsspring.controller.ApplicationController;
import org.prgrms.prgrmsspring.controller.CustomerController;
import org.prgrms.prgrmsspring.controller.VoucherController;
import org.prgrms.prgrmsspring.exception.ExceptionMessage;
import org.prgrms.prgrmsspring.exception.NotFoundException;
import org.prgrms.prgrmsspring.service.CustomerService;
import org.prgrms.prgrmsspring.service.VoucherService;
import org.prgrms.prgrmsspring.view.CommandLineView;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;
import java.util.function.Function;

public enum ControllerType {
    CUSTOMER(1, CustomerController.class, applicationContext -> {
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        CommandLineView commandLineView = applicationContext.getBean(CommandLineView.class);
        return new CustomerController(commandLineView, customerService);
    }),
    VOUCHER(2, VoucherController.class, applicationContext -> {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        CommandLineView commandLineView = applicationContext.getBean(CommandLineView.class);
        return new VoucherController(commandLineView, voucherService);
    });

    private final int modeNumber;
    private final Class<? extends ApplicationController> controllerClass;
    private final Function<ApplicationContext, ApplicationController> controllerInstantiator;

    ControllerType(int modeNumber, Class<? extends ApplicationController> controllerClass,
                   Function<ApplicationContext, ApplicationController> controllerInstantiator) {
        this.modeNumber = modeNumber;
        this.controllerClass = controllerClass;
        this.controllerInstantiator = controllerInstantiator;
    }

    public ApplicationController createController(ApplicationContext applicationContext) {
        return controllerInstantiator.apply(applicationContext);
    }

    private static ControllerType from(int modeNumber) {
        return Arrays.stream(ControllerType.values())
                .filter(c -> c.modeNumber == modeNumber)
                .findAny()
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.NOT_FOUND_CONTROLLER_TYPE.getMessage()));
    }


    public static ControllerType findControllerTypeForCommand(Command command) {
        return Arrays.stream(ControllerType.values())
                .filter(c -> c.controllerClass.equals(command.getControllerClass()))
                .findAny()
                .orElseThrow();
    }
}
