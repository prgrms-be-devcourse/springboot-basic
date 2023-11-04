package org.programmers.springboot.basic.domain.wallet.controller;

import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.customer.dto.CustomerResponseDto;
import org.programmers.springboot.basic.domain.customer.entity.Customer;
import org.programmers.springboot.basic.domain.customer.exception.CustomerNotFoundException;
import org.programmers.springboot.basic.domain.customer.exception.IllegalEmailException;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.wallet.dto.WalletRequestDto;
import org.programmers.springboot.basic.domain.wallet.dto.WalletResponseDto;
import org.programmers.springboot.basic.domain.wallet.exception.DuplicateWalletException;
import org.programmers.springboot.basic.domain.wallet.mapper.WalletMapper;
import org.programmers.springboot.basic.domain.wallet.service.WalletService;
import org.programmers.springboot.basic.util.manager.ConsoleIOManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Slf4j
@Controller
public class WalletController {

    private final ConsoleIOManager consoleIOManager;
    private final WalletService walletService;

    @Autowired
    public WalletController(ConsoleIOManager consoleIOManager, WalletService walletService) {
        this.consoleIOManager = consoleIOManager;
        this.walletService = walletService;
    }

    public void addWallet() {

        String email = null;
        String voucherId = null;

        consoleIOManager.printAssignHandler();

        try {
            email = consoleIOManager.getInput();
            voucherId = consoleIOManager.getInput();

            WalletRequestDto requestDto = WalletMapper.INSTANCE.mapToRequestDto(email, voucherId);
            this.walletService.createWallet(requestDto);
        } catch (IllegalArgumentException e) {
            log.warn("Your input is Invalid UUID string: '{}'", voucherId);
        } catch (IllegalEmailException e) {
            log.warn("Your input is Invalid Email Type: '{}'", email);
        } catch (DuplicateWalletException e) {
            log.warn("email '{}' and voucherId '{}' is already exists in Wallet", email, voucherId);
        } catch (CustomerNotFoundException e) {
            log.warn("No customer of email '{}' found", email);
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher of voucherId '{}' found", voucherId);
        }
    }

    public void findVoucherListByEmail() {

        String email = null;

        consoleIOManager.printListByConsumerHandler();

        try {
            email = consoleIOManager.getInput();

            WalletRequestDto requestDto = WalletMapper.INSTANCE.mapToRequestDtoWithEmail(email);
            List<WalletResponseDto> walletResponseDtos = this.walletService.walletListByEmail(requestDto);
            List<VoucherResponseDto> voucherResponseDtos = this.walletService.voucherListFromWallet(walletResponseDtos);
            Customer customer = this.walletService.findCustomerByEmail(requestDto);
            consoleIOManager.printWalletInfoByConsumer(customer, voucherResponseDtos);
        } catch (IllegalEmailException e) {
            log.warn("Your input is Invalid Email Type: '{}'", email);
        } catch (CustomerNotFoundException e) {
            log.warn("No customer of email '{}' found", email);
        }
    }


    public void findCustomerListByVoucherId() {

        String input = null;

        consoleIOManager.printListByVoucherHandler();

        try {
            input = consoleIOManager.getInput();
            WalletRequestDto requestDto = WalletMapper.INSTANCE.mapToRequestDtoWithUUID(input);
            List<WalletResponseDto> walletResponseDtos = this.walletService.walletListByVoucherId(requestDto);
            List<CustomerResponseDto> customerResponseDtos = this.walletService.customerListFromWallet(walletResponseDtos);
            Voucher voucher = this.walletService.findVoucherById(requestDto);
            consoleIOManager.printWalletInfoByVoucher(voucher, customerResponseDtos);
        } catch (IllegalArgumentException e) {
            log.warn("Your input is Invalid UUID string: '{}'", input);
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher of voucherId '{}' found", input);
        }
    }

    public void removeVoucherFromWallet() {

        String email = null;
        String voucherId = null;

        consoleIOManager.printRemoveVoucherFromWalletHandler();

        try {
            email = consoleIOManager.getInput();
            voucherId = consoleIOManager.getInput();
            WalletRequestDto requestDto = WalletMapper.INSTANCE.mapToRequestDto(email, voucherId);
            this.walletService.removeVoucherFromWallet(requestDto);
        } catch (IllegalArgumentException e) {
            log.warn("Your input is Invalid UUID string: '{}'", voucherId);
        } catch (IllegalEmailException e) {
            log.warn("Your input is Invalid Email Type: '{}'", email);
        } catch (CustomerNotFoundException e) {
            log.warn("No customer of email '{}' found", email);
        } catch (VoucherNotFoundException e) {
            log.warn("No voucher of voucherId '{}' found", voucherId);
        }
    }
}
