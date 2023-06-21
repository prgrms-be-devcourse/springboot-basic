package com.dev.voucherproject.config;


import com.dev.voucherproject.service.menus.CreateMenuExecutor;
import com.dev.voucherproject.service.menus.ListMenuExecutor;
import com.dev.voucherproject.service.menus.Menu;
import com.dev.voucherproject.service.VoucherService;
import com.dev.voucherproject.view.Console;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.dev.voucherproject"})
public class AppConfiguration {

    @Bean
    public ListMenuExecutor listMenuExecutor(VoucherService voucherService, Console console) {
        return new ListMenuExecutor(Menu.LIST, voucherService, console);
    }

    @Bean
    public CreateMenuExecutor createMenuExecutor(VoucherService voucherService, Console console) {
        return new CreateMenuExecutor(Menu.CREATE, voucherService, console);
    }
}
