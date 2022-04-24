package com.programmers.springbootbasic.consolestarter;

import com.programmers.springbootbasic.dto.CustomerDTO;
import com.programmers.springbootbasic.dto.StatusDTO;
import com.programmers.springbootbasic.dto.VoucherDTO;
import com.programmers.springbootbasic.service.CustomerService;
import com.programmers.springbootbasic.service.StatusService;
import com.programmers.springbootbasic.service.VoucherService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

import static com.programmers.springbootbasic.consolestarter.OutputConsolePrinter.*;
import static com.programmers.springbootbasic.validation.BusinessValidator.*;

public class VoucherManagementConsoleApp implements ConsoleApp {

    private static final String INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE = "잘못된 값입니다. 다시 입력하세요.";
    private static final Scanner sc = new Scanner(System.in);

    private boolean on = true;

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final StatusService statusService;

    public VoucherManagementConsoleApp(VoucherService voucherService, CustomerService customerService, StatusService statusService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.statusService = statusService;
    }

    @Override
    public void run() {
        while(on) {
            showMainMenu();

            try {
                int input = Integer.parseInt(sc.next());
                switch (input) {
                    case 1 -> doCustomerRelatedJob();
                    case 2 -> doVoucherRelatedJob();
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

    public void showMainMenu() {
        System.out.println("\n********************************************************************************************************************");
        System.out.println("원하시는 업무를 선택하세요.");
        System.out.println("1. 고객");
        System.out.println("2. 할인권");
        System.out.println("3. 프로그램 종료");
        System.out.println("********************************************************************************************************************");
        System.out.print("==> ");
    }

    public void doCustomerRelatedJob() {
        showCustomerRelatedMenu();

        int input = Integer.parseInt(sc.next());
        switch (input) {
            case 1 -> createNewCustomer();
            case 2 -> allocateVoucherToCustomer();
            case 3 -> searchVouchersByCustomerId();
            case 4 -> retrieveCustomerById();
            case 5 -> retrieveAllCustomers();
            case 6 -> deleteCustomer();
            case 7 -> deleteAllCustomers();
            case 8 -> System.out.println("처음으로 돌아갑니다.");
            default -> System.out.println(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
        }
    }

    public void showCustomerRelatedMenu() {
        System.out.println(DIVISION_LINE);
        System.out.println("1. 새로운 고객 생성");
        System.out.println("2. 고객에게 할인권 할당");
        System.out.println("3. 고객이 보유한 할인권 조회");
        System.out.println("4. 아이디로 고객 조회");
        System.out.println("5. 모든 고객 조회");
        System.out.println("6. 아이디로 고객 삭제");
        System.out.println("7. 모든 고객 삭제");
        System.out.println("8. 처음으로");
        System.out.println(DIVISION_LINE);
        System.out.print("==> ");
    }

    public void createNewCustomer() {
        String customerId = getValidCustomerId();
        String name = getValidCustomerName();
        CustomerDTO newCustomer = customerService.createCustomer(customerId, name);
        printNewCustomer(newCustomer);
    }

    public void allocateVoucherToCustomer() {
        String customerId = getValidCustomerId();
        String voucherId = getValidVoucherId();
        StatusDTO status = statusService.createStatus(customerId, UUID.fromString(voucherId));
        printAllocationAcknowledgement(status);
    }

    public void searchVouchersByCustomerId() {
        String customerId = getValidCustomerId();
        List<VoucherDTO> vouchersOfCustomer = statusService.findVouchersOfCustomer(customerId);
        printVouchersOfCustomer(customerId, vouchersOfCustomer);
    }

    public void retrieveCustomerById() {
        CustomerDTO foundCustomer = retrieveCustomer();
        printFoundCustomer(foundCustomer);
    }

    public void retrieveAllCustomers() {
        List<CustomerDTO> allCustomers = customerService.findAllCustomers();
        printAllCustomers(allCustomers);
    }

    public void deleteCustomer() {
        CustomerDTO customerToBeDeleted = retrieveCustomer();
        customerService.deleteCustomerById(customerToBeDeleted.getCustomerId());
        printDeletedCustomer(customerToBeDeleted);
    }

    private CustomerDTO retrieveCustomer() {
        String customerId = getValidCustomerId();
        Optional<CustomerDTO> foundCustomer = customerService.findCustomerById(customerId);
        return foundCustomer.orElseThrow(() -> new IllegalArgumentException(NON_EXISTING_CUSTOMER_ID_EXCEPTION_MESSAGE));
    }

    public void deleteAllCustomers() {
        String response = promptConsoleInput("정말로 모든 고객을 삭제하시겠습니까(y/n)?").toLowerCase();

        switch (response) {
            case "y" -> {
                customerService.deleteAllCustomers();
                System.out.println("모든 고객이 정상적으로 삭제되었습니다.");
            }
            case "n" -> System.out.println("실행이 취소되었습니다.");
            default -> System.out.println(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
        }
    }

    public void doVoucherRelatedJob() {
        showVoucherRelatedMenu();

        int input = Integer.parseInt(sc.next());
        switch (input) {
            case 1 -> createNewVoucher();
            case 2 -> retrieveVoucherById();
            case 3 -> searchAvailableVouchers();
            case 4 -> retrieveAllVouchers();
            case 5 -> retrieveCustomerByVoucherId();
            case 6 -> deleteVoucher();
            case 7 -> deleteAllVouchers();
            case 8 -> System.out.println("처음으로 돌아갑니다.");
            default -> System.out.println(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
        }
    }

    public void showVoucherRelatedMenu() {
        System.out.println(DIVISION_LINE);
        System.out.println("1. 새로운 할인권 생성");
        System.out.println("2. 아이디로 할인권 조회");
        System.out.println("3. 할당 가능한 할인권 조회");
        System.out.println("4. 모든 할인권 조회");
        System.out.println("5. 아이디로 해당 할인권 보유 고객 조회");
        System.out.println("6. 아이디로 할인권 삭제");
        System.out.println("7. 모든 할인권 삭제");
        System.out.println("8. 처음으로");
        System.out.println(DIVISION_LINE);
        System.out.print("==> ");
    }

    public void createNewVoucher() {
        String input = promptConsoleInput("할인권의 종류를 선택하세요.\n1. 고정금액 2. 고정비율");
        VoucherDTO newVoucher = voucherService.createVoucher(Integer.parseInt(input));
        printNewVoucher(newVoucher);
    }

    public void retrieveVoucherById() {
        VoucherDTO voucher = findAndGetVoucher();
        printFoundVoucher(voucher);
    }

    public void searchAvailableVouchers() {
        List<VoucherDTO> availableVouchers = voucherService.findAvailableVouchers();
        printAvailableVouchers(availableVouchers);
    }

    public void retrieveAllVouchers() {
        List<VoucherDTO> allVouchers = voucherService.findAllVouchers();
        printAllVouchers(allVouchers);
    }

    public void retrieveCustomerByVoucherId() {
        String voucherId = getValidVoucherId();
        Optional<CustomerDTO> foundCustomer = statusService.findCustomerHoldingVoucher(UUID.fromString(voucherId));
        printCustomerByVoucherId(foundCustomer.orElseThrow(() -> new IllegalArgumentException(NON_EXISTING_CUSTOMER_HOLDING_THIS_VOUCHER_EXCEPTION_MESSAGE)));
    }

    public void deleteVoucher() {
        VoucherDTO voucherToBeDeleted = findAndGetVoucher();
        voucherService.deleteVoucherById(voucherToBeDeleted.getVoucherId());
        printDeletedVoucher(voucherToBeDeleted);
    }

    private VoucherDTO findAndGetVoucher() {
        String voucherId = getValidVoucherId();
        Optional<VoucherDTO> foundVoucher = voucherService.findVoucherById(UUID.fromString(voucherId));
        return foundVoucher.orElseThrow(() -> new IllegalArgumentException(NON_EXISTING_VOUCHER_ID_EXCEPTION_MESSAGE));
    }

    public void deleteAllVouchers() {
        String response = promptConsoleInput("정말로 모든 할인권을 삭제하시겠습니까(y/n)?").toLowerCase();

        switch (response) {
            case "y" -> {
                voucherService.deleteAllVouchers();
                System.out.println("모든 할인권이 정상적으로 삭제되었습니다.");
            }
            case "n" -> System.out.println("실행이 취소되었습니다.");
            default -> System.out.println(INVALID_CONSOLE_PROMPT_INPUT_VALUE_MESSAGE);
        }
    }
    private String getValidCustomerId() {
        String customerId = promptConsoleInput("고객 아이디를 입력하세요.");

        if (!validateCustomerId(customerId))
            throw new IllegalArgumentException(INVALID_CUSTOMER_ID_EXCEPTION_MESSAGE);

        return customerId;
    }

    private String getValidCustomerName() {
        String name = promptConsoleInput("고객 이름을 입력하세요.");

        if (!validateCustomerName(name))
            throw new IllegalArgumentException(INVALID_CUSTOMER_NAME_EXCEPTION_MESSAGE);

        return name;
    }

    private String getValidVoucherId() {
        String voucherId = promptConsoleInput("할인권 아이디를 입력하세요.");

        if (!validateVoucherId(voucherId))
            throw new IllegalArgumentException(INVALID_VOUCHER_ID_EXCEPTION_MESSAGE);

        return voucherId;
    }

    private String promptConsoleInput(String promptMessage) {
        System.out.println(promptMessage);
        System.out.print("==> ");
        return sc.next();
    }

}
