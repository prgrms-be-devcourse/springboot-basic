package com.zerozae.voucher.common;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.controller.console.customer.CustomerController;
import com.zerozae.voucher.controller.console.voucher.VoucherController;
import com.zerozae.voucher.controller.console.wallet.WalletController;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.customer.CustomerCreateRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.customer.CustomerUpdateRequest;
import com.zerozae.voucher.dto.voucher.VoucherCreateRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.dto.wallet.WalletCreateRequest;
import com.zerozae.voucher.exception.ExceptionMessage;
import com.zerozae.voucher.view.ConsoleView;

import java.util.List;
import java.util.UUID;

@Deprecated
public class MenuHandler {

    private final ConsoleView consoleView;
    private final VoucherController voucherController;
    private final CustomerController customerController;
    private final WalletController walletController;

    public MenuHandler(ConsoleView consoleView, VoucherController voucherController,
                       CustomerController customerController,
                       WalletController walletController) {
        this.consoleView = consoleView;
        this.voucherController = voucherController;
        this.customerController = customerController;
        this.walletController = walletController;
    }

    public MenuType selectCommand(String program) {
        try {
            switch (program){
                case "mainProgram" -> consoleView.printCommand();
                case "customerProgram" -> consoleView.printCustomerCommand();
                case "voucherProgram" -> consoleView.printVoucherCommand();
                case "walletProgram" -> consoleView.printWalletCommand();
            }
            return MenuType.of(consoleView.inputCommand());
        }catch (ExceptionMessage e){
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void exit() {
        consoleView.printSystemMessage("프로그램을 종료합니다.");
    }

    public void createVoucher() {
        try {
            consoleView.printSystemMessage("바우처의 종류(Fixed / Percent)를 입력해주세요.");
            consoleView.printPrompt();
            VoucherType voucherType = VoucherType.of(consoleView.inputVoucherType());

            consoleView.printSystemMessage("바우처의 할인 정보를 입력해주세요.");
            consoleView.printPrompt();
            Long discount = consoleView.inputNumber();

            VoucherCreateRequest voucherRequest = new VoucherCreateRequest(discount, voucherType.toString());
            consoleView.printSystemMessage(voucherController.createVoucher(voucherRequest).getMessage());
        }catch (ExceptionMessage e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void findVoucherList() {
        Response<List<VoucherResponse>> vouchers = voucherController.findAllVouchers();
        List<VoucherResponse> data = vouchers.getData();
        data.forEach(voucher -> consoleView.printInfo(voucher.getInfo()));
    }

    public void findVoucherById() {
        try {
            consoleView.printSystemMessage("조회할 바우처의 아이디를 입력해주세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUuid());

            Response<VoucherResponse> result = voucherController.findVoucherById(voucherId);
            consoleView.printSystemMessage(result.getData().getInfo());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void deleteVoucherById() {
        try{
            consoleView.printSystemMessage("제거할 바우처의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUuid());

            consoleView.printSystemMessage(voucherController.deleteById(voucherId).getMessage());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void deleteAllVouchers() {
        try{
            consoleView.printSystemMessage("전체 바우처를 제거하겠습니다.");
            consoleView.printSystemMessage(voucherController.deleteAll().getMessage());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void updateVoucher() {
        try {
            consoleView.printSystemMessage("수정할 바우처의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID voucherId  = UUID.fromString(consoleView.inputUuid());

            consoleView.printSystemMessage("바우처 수정을 위해 할인율을 입력해주세요");
            consoleView.printPrompt();
            long newDiscount = consoleView.inputNumber();

            consoleView.printSystemMessage("바우처 수정을 위해 수정할 사용 타입을 입력해주세요");
            consoleView.printPrompt();
            UseStatusType newUseStatusType = UseStatusType.of(consoleView.inputVoucherType());

            VoucherUpdateRequest updateRequest = new VoucherUpdateRequest(newDiscount, newUseStatusType.toString());
            consoleView.printSystemMessage(voucherController.updateVoucher(voucherId, updateRequest).getMessage());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void createCustomer() {
        try {
            consoleView.printSystemMessage("회원의 이름을 입력해주세요.");
            consoleView.printPrompt();
            String customerName = consoleView.inputCustomerName();

            consoleView.printSystemMessage("회원 타입(NORMAL,BLACKLIST)을 입력해주세요.");
            consoleView.printPrompt();
            CustomerType customerType = CustomerType.of(consoleView.inputCustomerType());

            CustomerCreateRequest customerRequest = new CustomerCreateRequest(customerName, customerType.toString());
            consoleView.printSystemMessage(customerController.createCustomer(customerRequest).getMessage());
        }catch (ExceptionMessage e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void findCustomerList() {
        Response<List<CustomerResponse>> customers = customerController.findAllCustomers();
        List<CustomerResponse> data = customers.getData();
        data.forEach(customer -> consoleView.printInfo(customer.getInfo()));
    }

    public void findCustomerBlacklist() {
        Response<List<CustomerResponse>> blacklistCustomers = customerController.findAllBlacklistCustomers();
        List<CustomerResponse> data = blacklistCustomers.getData();
        data.forEach(customer -> consoleView.printInfo(customer.getInfo()));
    }

    public void findCustomerById() {
        try {
            consoleView.printSystemMessage("조회할 회원의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUuid());

            Response<CustomerResponse> data = customerController.findCustomerById(customerId);
            consoleView.printSystemMessage(data.getData().getInfo());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void updateCustomer() {
        try {
            consoleView.printSystemMessage("수정할 회원의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId  = UUID.fromString(consoleView.inputUuid());

            consoleView.printSystemMessage("이름을 수정하기 위해 새로운 이름을 입력하세요.");
            consoleView.printPrompt();
            String newCustomerName = consoleView.inputCustomerName();

            consoleView.printSystemMessage("회원 타입을 입력하세요.");
            consoleView.printPrompt();
            CustomerType newCustomerType = CustomerType.of(consoleView.inputCustomerType());
            CustomerUpdateRequest customerRequest = new CustomerUpdateRequest(newCustomerName, newCustomerType.toString());

            consoleView.printSystemMessage(customerController.updateCustomer(customerId, customerRequest).getMessage());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void deleteCustomerById() {
        try{
            consoleView.printSystemMessage("제거할 회원의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUuid());

            consoleView.printSystemMessage(customerController.deleteCustomerById(customerId).getMessage());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void deleteAllCustomers() {
        try{
            consoleView.printSystemMessage("전체 회원을 제거하겠습니다.");
            consoleView.printSystemMessage(customerController.deleteAllCustomers().getMessage());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void assignVoucherToCustomer() {
        try {
            consoleView.printSystemMessage("바우처를 부여할 회원 번호를 입력하세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUuid());

            consoleView.printSystemMessage("등록할 바우처의 번호를 입력하세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUuid());

            WalletCreateRequest walletRequest = new WalletCreateRequest(customerId.toString(), voucherId.toString());
            consoleView.printSystemMessage(walletController.createWallet(walletRequest).getMessage());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void removeVoucherFromCustomer() {
        try {
            consoleView.printSystemMessage("바우처를 제거할 회원 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUuid());

            consoleView.printSystemMessage("제거할 바우처의 번호를 입력하세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUuid());
            consoleView.printSystemMessage(walletController.deleteWalletsById(customerId, voucherId).getMessage());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void voucherListOwnedByCustomer() {
        try {
            consoleView.printSystemMessage("소유하고 있는 바우처 목록을 조회할 회원 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUuid());

            Response<List<VoucherResponse>> result = walletController.findWalletByCustomerId(customerId);
            result.getData()
                    .forEach(voucher -> consoleView.printInfo(voucher.getInfo()));
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void findVoucherOwnerList() {
        try {
            consoleView.printSystemMessage("소유자를 찾고자 하는 바우처 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUuid());

            Response<List<CustomerResponse>> result = walletController.findWalletByVoucherId(voucherId);
            result.getData()
                    .forEach(customer -> consoleView.printInfo(customer.getInfo()));
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }

    public void deleteAllWallets() {
        try{
            consoleView.printSystemMessage("지갑 전체를 초기화합니다.");
            consoleView.printSystemMessage(walletController.deleteAllWallets().getMessage());
        }catch (Exception e) {
            throw ExceptionMessage.error(e.getMessage());
        }
    }
}
