package com.prgrms.commandLineApplication.controller;

import com.prgrms.commandLineApplication.customer.Customer;
import com.prgrms.commandLineApplication.io.Console;
import com.prgrms.commandLineApplication.io.MenuType;
import com.prgrms.commandLineApplication.service.CustomerService;
import com.prgrms.commandLineApplication.service.VoucherService;
import com.prgrms.commandLineApplication.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.NoSuchElementException;

@Controller
public class VoucherController {

  private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

  private final VoucherService voucherService;
  private final CustomerService customerService;
  private final Console console;

  public VoucherController(VoucherService voucherService, CustomerService customerService, Console console) {
    this.voucherService = voucherService;
    this.customerService = customerService;
    this.console = console;
  }

  public void run() {
    boolean isRunning = true;

    while (isRunning) {
      try {
        console.printMenu();

        String enterMenu = console.readMenu();
        MenuType menuType = MenuType.valueOfType(enterMenu);

        switch (menuType) {
          case EXIT -> isRunning = false;
          case VOUCHERLIST -> voucherList();
          case CREATEVOUCHER -> createVoucher();
          case CUSTOMERLIST -> customerList();
          case CREATECUSTOMER -> createCustomer();
        }
      } catch (IllegalArgumentException e) {
        logger.error("Error Message => {}", e.getMessage(), e);
        console.printErrorMessage(e);
      } catch (NoSuchElementException e) {
        logger.warn("Warn Message => {}", e.getMessage(), e);
        console.printErrorMessage(e);
      }
    }
  }

  private void voucherList() {
    List<Voucher> list = voucherService.findAllVouchers();
    console.printAllVouchers(list);
  }

  private void createVoucher() {
    console.requestVoucherType();
    String voucherType = console.readVoucherType();

    console.requestDiscountAmount();
    int discountAmount = console.readVoucherAmount();

    voucherService.create(voucherType, discountAmount);
    console.printCreateVoucherSuccess(voucherType, discountAmount);
  }

  private void customerList() {
    List<Customer> list = customerService.findAllCustomers();
    console.printAllCustomers(list);
  }

  private void createCustomer() {
    console.requestCustomerName();
    String customerName = console.readCustomerName();

    console.requestCustomerEmail();
    String email = console.readCustomerEmail();

    customerService.create(customerName, email);
    console.printCreateCustomerSuccess(customerName, email);
  }

}
