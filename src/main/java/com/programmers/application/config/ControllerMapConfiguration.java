package com.programmers.application.config;

import com.programmers.application.controller.console.Controller;
import com.programmers.application.controller.console.ExitController;
import com.programmers.application.controller.console.ServiceCommand;
import com.programmers.application.controller.console.VoucherController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class ControllerMapConfiguration {

    private final VoucherController voucherController;
    private final ExitController exitController;

    public ControllerMapConfiguration(VoucherController voucherController, ExitController exitController) {
        this.voucherController = voucherController;
        this.exitController = exitController;
    }

    @Bean
    public Map<ServiceCommand, Controller> controllerMap() {
        EnumMap<ServiceCommand, Controller> controllerMap = new EnumMap<>(ServiceCommand.class);
        controllerMap.put(ServiceCommand.VOUCHER, voucherController);
        controllerMap.put(ServiceCommand.EXIT, exitController);
        return controllerMap;
    }
}
