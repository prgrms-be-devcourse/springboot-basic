package com.dev.voucherproject.config;


import com.dev.voucherproject.controller.console.CreateMenuExecutor;
import com.dev.voucherproject.controller.console.ListMenuExecutor;
import com.dev.voucherproject.model.menu.Menu;
import com.dev.voucherproject.model.voucher.VoucherDataAccessor;
import com.dev.voucherproject.view.Console;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsoleConfiguration {

    @Bean
    public ListMenuExecutor listMenuExecutor(VoucherDataAccessor voucherDataAccessor, Console console) {
        return new ListMenuExecutor(Menu.LIST, voucherDataAccessor, console);
    }

    @Bean
    public CreateMenuExecutor createMenuExecutor(VoucherDataAccessor voucherDataAccessor, Console console) {
        return new CreateMenuExecutor(Menu.CREATE, voucherDataAccessor, console);
    }
}
