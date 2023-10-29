package com.programmers.vouchermanagement.view;

import com.programmers.vouchermanagement.domain.customer.Customer;
import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.CustomerDto;
import com.programmers.vouchermanagement.dto.VoucherDto;
import com.programmers.vouchermanagement.message.ConsoleMessage;
import com.programmers.vouchermanagement.message.CustomerMessage;
import com.programmers.vouchermanagement.message.VoucherMessage;
import com.programmers.vouchermanagement.message.WalletMessage;
import com.programmers.vouchermanagement.service.CustomerService;
import com.programmers.vouchermanagement.service.VoucherService;
import com.programmers.vouchermanagement.service.WalletService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.programmers.vouchermanagement.utils.ConsoleUtil.*;

@Component
public class Console implements CommandLineRunner {
    private static final float DISCOUNT_AMOUNT_MIN_VALUE = 0f;

    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;
    private final Map<Integer, Runnable> mainCommandMap = new HashMap<>();
    private final Map<Integer, Runnable> voucherCommandMap = new HashMap<>();
    private final Map<Integer, Runnable> customerCommandMap = new HashMap<>();
    private final Map<Integer, Runnable> wallletCommandMap = new HashMap<>();
    private final TextIO textIO = TextIoFactory.getTextIO();
    private final Logger logger = LoggerFactory.getLogger(Console.class);

    public Console(VoucherService voucherService, CustomerService customerService, WalletService walletService) {
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
    }

    @PostConstruct
    private void init() {
        mainCommandMap.put(0, this::exit);  // 0. 종료
        mainCommandMap.put(1, () -> executeMenu(voucherCommandMap, VoucherMessage.VOUCHER_COMMAND_LIST_MESSAGE.getMessage()));  // 1. 바우처 관리
        mainCommandMap.put(2, () -> executeMenu(customerCommandMap, CustomerMessage.CUSTOMER_COMMAND_LIST_MESSAGE.getMessage()));  // 2. 고객 관리
        mainCommandMap.put(3, () -> executeMenu(wallletCommandMap, WalletMessage.WALLET_COMMAND_LIST_MESSAGE.getMessage()));  // 3. 고객 - 바우처 메뉴

        voucherCommandMap.put(1, this::displayVoucherList); // 1. 전체 목록
        voucherCommandMap.put(2, this::findVoucherById);   // 2. ID로 검색
        voucherCommandMap.put(3, this::findVoucherByName);   // 3. 이름으로 검색
        voucherCommandMap.put(4, this::createVoucher);   // 4. 바우처 생성
        voucherCommandMap.put(5, this::deleteVoucher);   // 5. 바우처 삭제

        customerCommandMap.put(1, this::displayCustomerList); // 1. 전체 목록
        customerCommandMap.put(2, this::findCustomerById);   // 2. ID로 검색
        customerCommandMap.put(3, this::findCustomerByName);   // 3. 이름으로 검색
        customerCommandMap.put(4, this::displayCustomerBlackList);   // 4. 고객 블랙리스트 조회
        customerCommandMap.put(5, this::createCustomer);   // 5. 고객 생성
        customerCommandMap.put(6, this::deleteCustomer);   // 6. 고객 삭제

        wallletCommandMap.put(1, this::findVoucherByCustomer); // 1. 고객이 보유한 바우처 조회
        wallletCommandMap.put(2, this::findCustomerByVoucher); // 2. 특정 바우처를 보유한 고객 조회
        wallletCommandMap.put(3, this::giveVoucherToCustomer); // 3. 고객에게 바우처 지급
        wallletCommandMap.put(4, this::deleteVoucherFromCustomer); // 4. 고객에게 지급한 바우처 삭제

        displayMessage(ConsoleMessage.WELCOME_MESSAGE.getMessage());
        logger.info("VoucherManagementApplication initialized");
    }

    @Override
    public void run(String... args) {
        Integer command = -1;
        while (command != 0) {
            try {
                command = getCommand();
                mainCommandMap.get(command).run();
            } catch (Exception e) {
                displayMessage(e.getMessage());
                logger.error(MessageFormat.format("Error occurred: {0}", e));
            }
        }
    }

    private Integer getCommand() {
        displayMessage(ConsoleMessage.MAIN_COMMAND_LIST_MESSAGE.getMessage());
        Integer command = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(mainCommandMap.size() - 1)
                .read(">");
        logger.info("Input Command: {}", command);
        return command;
    }

    private void executeMenu(Map<Integer, Runnable> commandMap, String message) {
        displayMessage(message);
        Integer command = textIO.newIntInputReader()
                .withMinVal(0)
                .withMaxVal(commandMap.size())
                .read(">");
        if (command == 0) return;
        commandMap.get(command).run();
    }

    //Voucher
    private void displayVoucherList() {
        voucherService.findAllVouchers()
                .forEach(voucher -> displayMessage(voucher.toString()));
    }

    private void findVoucherById() {
        displayMessage(VoucherMessage.INPUT_VOUCHER_ID_MESSAGE.getMessage());
        Voucher voucher = voucherService.findByVoucherId(getUUID());
        displayMessage(voucher.toString());
    }

    private void findVoucherByName() {
        displayMessage(VoucherMessage.INPUT_VOUCHER_NAME_MESSAGE.getMessage());
        voucherService.findByVoucherName(getName()).forEach(voucher -> displayMessage(voucher.toString()));
    }

    private void createVoucher() {
        displayMessage(VoucherMessage.SELECT_VOUCHER_TYPE_MESSAGE.getMessage());
        VoucherType voucherType = textIO.newEnumInputReader(VoucherType.class)
                .withAllValues()
                .read();

        String voucherName = getName(voucherType.getGuideMessage());
        float discountAmount = textIO.newFloatInputReader()
                .withMinVal(DISCOUNT_AMOUNT_MIN_VALUE)
                .read(VoucherMessage.INPUT_DISCOUNT_AMOUNT_MESSAGE.getMessage());

        Voucher voucher = voucherService.createVoucher(new VoucherDto.Create(voucherName, discountAmount, voucherType));
        displayMessage(voucher.toString());
        displayMessage(VoucherMessage.VOUCHER_CREATED_MESSAGE.getMessage());
    }

    private void deleteVoucher() {
        displayMessage(VoucherMessage.INPUT_VOUCHER_ID_MESSAGE.getMessage());
        voucherService.deleteVoucher(getUUID());
        displayMessage(VoucherMessage.VOUCHER_DELETED_MESSAGE.getMessage());
    }

    //Customer
    private void displayCustomerList() {
        customerService.findAllCustomers()
                .forEach(customer -> displayMessage(customer.toString()));
    }

    private void findCustomerById() {
        displayMessage(CustomerMessage.INPUT_CUSTOMER_ID_MESSAGE.getMessage());
        Customer customer = customerService.findCustomerById(getUUID());
        displayMessage(customer.toString());
    }

    private void findCustomerByName() {
        displayMessage(CustomerMessage.INPUT_CUSTOMER_NAME_MESSAGE.getMessage());
        customerService.findCustomerByName(getName()).forEach(customer -> displayMessage(customer.toString()));
    }

    private void displayCustomerBlackList() {
        customerService.findBannedCustomers()
                .forEach(customer -> displayMessage(customer.toString()));
    }

    private void createCustomer() {
        String customerName = getName(CustomerMessage.INPUT_CUSTOMER_NAME_MESSAGE.getMessage());
        Customer customer = customerService.createCustomer(new CustomerDto.Create(customerName));
        displayMessage(customer.toString());
        displayMessage(CustomerMessage.CUSTOMER_CREATED_MESSAGE.getMessage());
    }

    private void deleteCustomer() {
        displayMessage(CustomerMessage.INPUT_CUSTOMER_ID_MESSAGE.getMessage());
        customerService.deleteCustomer(getUUID());
        displayMessage(CustomerMessage.CUSTOMER_DELETED_MESSAGE.getMessage());
    }

    private void findVoucherByCustomer() {
        displayMessage(CustomerMessage.INPUT_CUSTOMER_ID_MESSAGE.getMessage());
        walletService.findVoucherByCustomer(getUUID())
                .forEach(voucher -> displayMessage(voucher.toString()));
    }

    private void findCustomerByVoucher() {
        displayMessage(VoucherMessage.INPUT_VOUCHER_ID_MESSAGE.getMessage());
        walletService.findCustomerByVoucher(getUUID())
                .forEach(customer -> displayMessage(customer.toString()));
    }

    private void giveVoucherToCustomer() {
        displayMessage(CustomerMessage.GIVE_VOUCHER_TO_CUSTOMER_MESSAGE.getMessage());
        UUID customerId = getUUID(CustomerMessage.INPUT_CUSTOMER_ID_MESSAGE.getMessage());
        UUID voucherId = getUUID(VoucherMessage.INPUT_VOUCHER_ID_MESSAGE.getMessage());
        displayMessage(walletService.giveVoucherToCustomer(customerId, voucherId).toString());
        displayMessage(WalletMessage.WALLET_GIVEN_SUCCESS_MESSAGE.getMessage());
    }

    private void deleteVoucherFromCustomer() {
        displayMessage(CustomerMessage.DELETE_VOUCHER_FROM_CUSTOMER_MESSAGE.getMessage());
        UUID customerId = getUUID(CustomerMessage.INPUT_CUSTOMER_ID_MESSAGE.getMessage());
        UUID voucherId = getUUID(VoucherMessage.INPUT_VOUCHER_ID_MESSAGE.getMessage());
        walletService.deleteVoucherFromCustomer(customerId, voucherId);
        displayMessage(WalletMessage.WALLET_DELETED_MESSAGE.getMessage());
    }

    private void exit() {
        displayMessage(ConsoleMessage.EXIT_MESSAGE.getMessage());
    }

    @PreDestroy
    private void destroy() {
        logger.info("VoucherManagementApplication stopped");
    }
}
