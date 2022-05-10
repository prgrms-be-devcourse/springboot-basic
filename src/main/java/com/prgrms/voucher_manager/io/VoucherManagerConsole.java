package com.prgrms.voucher_manager.io;

import com.prgrms.voucher_manager.customer.Customer;
import com.prgrms.voucher_manager.customer.controller.CustomerDto;
import com.prgrms.voucher_manager.customer.service.CustomerService;
import com.prgrms.voucher_manager.infra.facade.VoucherServiceFacade;
import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.controller.VoucherDto;
import com.prgrms.voucher_manager.voucher.service.VoucherService;
import com.prgrms.voucher_manager.wallet.service.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class VoucherManagerConsole {
    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerService customerService;
    private final WalletService walletService;
    private final VoucherServiceFacade voucherServiceFacade;
    private static final Logger logger = LoggerFactory.getLogger(VoucherManagerConsole.class);

    public VoucherManagerConsole(Input input, Output output, VoucherService voucherService, CustomerService customerService, WalletService walletService, VoucherServiceFacade voucherServiceFacade) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
        this.walletService = walletService;
        this.voucherServiceFacade = voucherServiceFacade;
    }

    public void run() throws IOException {
        while(true){
            output.consoleMenu();
            String select = input.selectOption();
            try{
                Command selectService = Command.getCommand(select);
                switch (selectService){
                    case VOUCHER:
                        voucherMenu();
                        break;
                    case CUSTOMER:
                        customerMenu();
                        break;
                    case EXIT:
                        output.exitProgram();
                        System.exit(0);
                        break;
                    default:
                        logger.info("wrong input {}",selectService);
                        output.wrongInput();
                        break;
                }
            }catch (IllegalArgumentException e){
                logger.info("console Menu wrong input : {} ",select);
            }
        }
    }

    private void voucherMenu() throws IOException {
        while(true){
            output.voucherMenu();
            String select = input.selectOption();
            try {
                Command command = Command.getCommand(select);
                switch (command) {
                    case CREATE:
                        output.createVoucher();
                        String createVoucherType = input.selectOption();
                        long value = Long.parseLong(input.input("원하는 할인 양을 입력해주세요. "));
                        voucherService.createVoucher(createVoucherType, value);
                        break;
                    case LIST:
                        voucherService.getFindAllVoucher();
                        break;
                    case CUSTOMER:
                        output.ListCustomerByVoucherType();
                        String voucherType = input.selectOption();
                        voucherServiceFacade.findCustomerByVoucherType(voucherType);
                        break;
                    case RETURN:
                        return;
                    default:
                        logger.info("wrong input {}",command);
                        output.wrongInput();
                        break;
                }
            } catch (IllegalArgumentException e) {
                logger.info("console Menu wrong input : {} ",select);
            }
        }

    }
    private void customerMenu() throws IOException {
        while(true){
            output.customerMenu();
            String select = input.selectOption();
            try {
                Command command = Command.getCommand(select);
                switch (command) {
                    case CREATE:
                        String name = input.input("이름을 입력해주세요. ");
                        String email = input.input("email 을 입력해주세요.");
                        customerService.createCustomer(name, email);
                        break;
                    case VOUCHER:
                        List<CustomerDto> customers = customerService.findAllCustomer();
                        int customerIndex = Integer.parseInt(input.input("바우처를 관리할 고객을 골라주세요."));
                        CustomerDto customer = customers.get(customerIndex);
//                        customerService.updateCustomer(customer.getCustomerId());
                        walletMenu(customer.getCustomerId());
                        break;
                    case LIST:
                        customerService.findAllCustomer();
                        break;
                    case BLACKLIST:
                        customerService.findAllBlackCustomer();
                        break;
                    case RETURN:
                        return;
                    default:
                        logger.info("wrong input {}",command);
                        output.wrongInput();
                        break;
                }
            } catch (IllegalArgumentException e) {
                logger.info("console Menu wrong input : {} {}",select ,e);
            }
        }
    }
    private void walletMenu(UUID customerId) throws IOException {
        while(true){
            output.walletMenu();
            String select = input.selectOption();
            try {
                Command command = Command.getCommand(select);
                switch (command) {
                    case INSERT:
                        List<VoucherDto> findAllVoucher = voucherService.getFindAllVoucher();
                        int voucherIndex = Integer.parseInt(input.input("할당할 바우처를 골라주세요 (번호 입력) : "));
                        UUID voucherId = findAllVoucher.get(voucherIndex).getVoucherId();
                        walletService.createWallet(customerId, voucherId);
                        break;
                    case LIST:
                        voucherServiceFacade.findVoucherByCustomerId(customerId);
                        break;
                    case DELETE:
                        List<VoucherDto> vouchers = voucherServiceFacade.findVoucherByCustomerId(customerId);
                        int index = Integer.parseInt(input.input("지갑에서 삭제할 바우처를 골라주세요 (번호 입력) : "));
                        walletService.deleteWallet(customerId, vouchers.get(index).getVoucherId());
                        break;
                    case RETURN:
                        return;
                    default:
                        logger.info("wrong input {}",command);
                        output.wrongInput();
                        break;
                }
            } catch (IllegalArgumentException e) {
                logger.info("console Menu wrong input : {} {}",select ,e);
            }
        }
    }
}
