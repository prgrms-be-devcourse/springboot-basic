package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.customer.constant.CustomerCommand;
import org.prgrms.kdtspringdemo.customer.model.dto.CustomerResponseDto;
import org.prgrms.kdtspringdemo.customer.service.CustomerService;
import org.prgrms.kdtspringdemo.view.constant.MainCommandType;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherCommand;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.dto.VoucherResponseDto;
import org.prgrms.kdtspringdemo.voucher.service.VoucherService;
import org.prgrms.kdtspringdemo.view.console.VoucherConsole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CommandLineApplication implements CommandLineRunner {
    private static final String MAIN_PROGRAM_INIT_MESSAGE = """
            === Voucher Program ===
            EXIT -> 프로그램 종료
            VOUCHER -> 바우처 관련 서비스
            CUSTOMER -> 소비자 관련 서비스
            """;
    private static final String SYSTEM_SHUTDOWN_MESSAGE = "시스템을 종료합니다.\n";
    private static final String VOUCHER_SERVICE_INIT_MESSAGE = """
            === Voucher Program ===
            CREATE -> 바우처 생성
            FIND_ID -> 특정 바우처 조회
            FIND_ALL -> 바우처 전체 조회
            UPDATE -> 특정 바우처 업데이트
            DELETE -> 특정 바우처 삭제
            """;
    private static final String CHOICE_VOUCHER_TYPE_MESSAGE = "바우처 타입을 입력하세요.(ex : FIXED or PERCENT)\n";
    private static final String AMOUNT_VOUCHER_MESSAGE = "할인 금액을 입력하세요.\n";
    private static final String PRINT_VOUCHER_INFO_MESSAGE = """
            id : %s
            type : %s
            discount amount : %s
            """;
    private static final String VOUCHER_ID_MESSAGE = "바우처 Id를 입력하세요.\n";
    private static final String CUSTOMER_SERVICE_INIT_MESSAGE = """
            === Voucher Program ===
            CREATE -> 소비자 생성
            FIND_ID -> 특정 소비자 ID로 조회
            FIND_NICKNAME -> 특정 소비자 닉네임으로 조회
            FIND_ALL -> 소비자 전체 조회
            UPDATE -> 특정 소비자 업데이트
            DELETE -> 특정 소비자 삭제
            """;
    private static final String INPUT_CUSTOMER_NICKNAME_MESSAGE = "닉네임을 입력해주세요.\n";
    private static final String PRINT_CUSTOMER_INFO_MESSAGE = """
            id : %s
            nickname : %s
            """;
    private static final String CUSTOMER_ID_MESSAGE = "소비자 Id를 입력하세요.\n";

    private final VoucherConsole voucherConsole = new VoucherConsole();
    private final VoucherService voucherService;
    private final CustomerService customerService;

    public CommandLineApplication(VoucherService voucherService, CustomerService customerService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) {
        MainCommandType userCommand = voucherConsole.inputMainCommand(MAIN_PROGRAM_INIT_MESSAGE);

        while (userCommand.isRunning()) {
            executeCommand(userCommand);
            userCommand = voucherConsole.inputMainCommand(MAIN_PROGRAM_INIT_MESSAGE);
        }
    }

    private void executeCommand(MainCommandType commandtype) {
        switch (commandtype) {
            case EXIT -> voucherConsole.printMessage(SYSTEM_SHUTDOWN_MESSAGE);
            case VOUCHER -> runVoucherService();
            case CUSTOMER -> runCustomerService();
        }
    }

    private void runVoucherService() {
        VoucherCommand userCommand = voucherConsole.inputVoucherCommand(VOUCHER_SERVICE_INIT_MESSAGE);

        switch (userCommand) {
            case CREATE -> createVoucher();
            case FIND_ID -> getVoucher();
            case FIND_ALL -> getAllVoucher();
            case UPDATE -> updateVoucher();
            case DELETE -> deleteVoucher();
        }
    }

    private void createVoucher() {
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE_MESSAGE);
        Long userAmount = voucherConsole.inputAmountByVoucher(AMOUNT_VOUCHER_MESSAGE);

        VoucherResponseDto voucherResponseDto = voucherService.create(userVoucherType, userAmount);
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO_MESSAGE, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
    }

    private void getVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID_MESSAGE);

        VoucherResponseDto voucherResponseDto = voucherService.findById(userVoucherId);
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO_MESSAGE, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
    }

    private void getAllVoucher() {
        List<VoucherResponseDto> vouchers = voucherService.findAll();
        for (VoucherResponseDto voucherResponseDto : vouchers) {
            voucherConsole.printVoucher(PRINT_VOUCHER_INFO_MESSAGE, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
        }
    }

    private void updateVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID_MESSAGE);
        VoucherType userVoucherType = voucherConsole.chooseVoucherType(CHOICE_VOUCHER_TYPE_MESSAGE);
        Long userAmount = voucherConsole.inputAmountByVoucher(AMOUNT_VOUCHER_MESSAGE);

        VoucherResponseDto voucherResponseDto = voucherService.update(userVoucherId, userVoucherType, userAmount);
        voucherConsole.printVoucher(PRINT_VOUCHER_INFO_MESSAGE, voucherResponseDto.getVoucherId(), voucherResponseDto.getVoucherType(), voucherResponseDto.getAmount());
    }

    private void deleteVoucher() {
        UUID userVoucherId = voucherConsole.inputVoucherId(VOUCHER_ID_MESSAGE);

        voucherService.delete(userVoucherId);
    }

    private void runCustomerService() {
        CustomerCommand userCommand = voucherConsole.inputCustomerCommand(CUSTOMER_SERVICE_INIT_MESSAGE);

        switch (userCommand) {
            case CREATE -> createCustomer();
            case FIND_ID -> findByIdCustomer();
            case FIND_NICKNAME -> findByNicknameCustomer();
            case FIND_ALL -> findAllCustomer();
            case UPDATE -> updateCustomer();
            case DELETE -> deleteCustomer();
        }
    }

    private void createCustomer() {
        String userNickname = voucherConsole.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME_MESSAGE);

        CustomerResponseDto responseDto = customerService.create(userNickname);
        voucherConsole.printCustomer(PRINT_CUSTOMER_INFO_MESSAGE, responseDto.getCustomerId(), responseDto.getNickname());
    }

    private void findByIdCustomer() {
        UUID userCustomerId = voucherConsole.inputCustomerId(CUSTOMER_ID_MESSAGE);

        CustomerResponseDto customerResponseDto = customerService.findById(userCustomerId);
        voucherConsole.printCustomer(PRINT_CUSTOMER_INFO_MESSAGE, customerResponseDto.getCustomerId(), customerResponseDto.getNickname());
    }

    private void findByNicknameCustomer() {
        String userNickname = voucherConsole.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME_MESSAGE);

        CustomerResponseDto customerResponseDto = customerService.findByNickname(userNickname);
        voucherConsole.printCustomer(PRINT_CUSTOMER_INFO_MESSAGE, customerResponseDto.getCustomerId(), customerResponseDto.getNickname());
    }

    private void findAllCustomer() {
        List<CustomerResponseDto> customers = customerService.findAll();
        for (CustomerResponseDto customerResponseDto : customers) {
            voucherConsole.printCustomer(PRINT_CUSTOMER_INFO_MESSAGE, customerResponseDto.getCustomerId(), customerResponseDto.getNickname());
        }
    }

    private void updateCustomer() {
        UUID userCustomerId = voucherConsole.inputCustomerId(CUSTOMER_ID_MESSAGE);
        String userNickname = voucherConsole.inputCustomerNickname(INPUT_CUSTOMER_NICKNAME_MESSAGE);

        CustomerResponseDto customerResponseDto = customerService.update(userCustomerId, userNickname);
        voucherConsole.printCustomer(PRINT_CUSTOMER_INFO_MESSAGE, customerResponseDto.getCustomerId(), customerResponseDto.getNickname());
    }

    private void deleteCustomer() {
        UUID userCustomerId = voucherConsole.inputCustomerId(CUSTOMER_ID_MESSAGE);

        customerService.delete(userCustomerId);
    }
}
