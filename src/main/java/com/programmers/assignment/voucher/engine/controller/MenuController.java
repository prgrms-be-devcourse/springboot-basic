package com.programmers.assignment.voucher.engine.controller;

import com.programmers.assignment.voucher.engine.service.CustomerService;
import com.programmers.assignment.voucher.engine.service.MenuService;
import org.springframework.stereotype.Controller;


@Controller
public class MenuController {
    private final MenuService menuService;
    private final CustomerService customerService;


    public MenuController(MenuService menuService, CustomerService customerService) {
        this.menuService = menuService;
        this.customerService = customerService;
    }


    public String startMenu() {
        return menuService.inputCommand();
    }

    public String createVoucherCommand() {
        String discountWay = menuService.createVoucher();
        return discountWay;
    }

    public void listCommand() {
        menuService.showVouchers();
    }

    public void exitCommand() {
        menuService.exitApplication();
    }
    
    public void createCustomerCommand() {
        customerService.createCustomer();
    }
}
