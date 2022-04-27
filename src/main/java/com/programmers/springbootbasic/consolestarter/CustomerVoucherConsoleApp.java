package com.programmers.springbootbasic.consolestarter;

import com.programmers.springbootbasic.service.CustomerService;
import com.programmers.springbootbasic.service.CustomerVoucherLogService;
import com.programmers.springbootbasic.service.VoucherService;

import static com.programmers.springbootbasic.consolestarter.InputUtils.sc;

public class MainConsoleApp implements ConsoleApp {

    public static final String INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE = "잘못된 값입니다. 다시 입력하세요.";

    private boolean on = true;

    private final CustomerManager customerManager;
    private final VoucherManager voucherManager;

    public MainConsoleApp(VoucherService voucherService, CustomerService customerService, CustomerVoucherLogService customerVoucherLogService) {
        voucherManager = new VoucherManager(voucherService, customerVoucherLogService);
        customerManager = new CustomerManager(customerService, customerVoucherLogService);
    }

    @Override
    public void run() {
        while(on) {
            showMainMenu();

            try {
                int input = Integer.parseInt(sc.next());
                switch (input) {
                    case 1 -> customerManager.serviceCustomerWork();
                    case 2 -> voucherManager.serviceVoucherWork();
                    case 3 -> {System.out.println("이용해 주셔서 감사합니다.");
                        on = false;}
                    default -> System.out.println(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
                }
            }
            catch (NumberFormatException e) {
                System.out.println(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void showMainMenu() {
        System.out.println("\n********************************************************************************************************************");
        System.out.println("원하시는 업무를 선택하세요.");
        System.out.println("1. 고객");
        System.out.println("2. 할인권");
        System.out.println("3. 프로그램 종료");
        System.out.println("********************************************************************************************************************");
        System.out.print("==> ");
    }

}