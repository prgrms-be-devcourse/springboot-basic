package com.zerozae.voucher.common;

import com.zerozae.voucher.common.response.Response;
import com.zerozae.voucher.controller.customer.CustomerController;
import com.zerozae.voucher.controller.voucher.VoucherController;
import com.zerozae.voucher.domain.customer.CustomerType;
import com.zerozae.voucher.domain.voucher.UseStatusType;
import com.zerozae.voucher.domain.voucher.VoucherType;
import com.zerozae.voucher.dto.customer.CustomerRequest;
import com.zerozae.voucher.dto.customer.CustomerResponse;
import com.zerozae.voucher.dto.voucher.VoucherRequest;
import com.zerozae.voucher.dto.voucher.VoucherResponse;
import com.zerozae.voucher.dto.voucher.VoucherUpdateRequest;
import com.zerozae.voucher.exception.ErrorMessage;
import com.zerozae.voucher.view.ConsoleView;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
public class MenuHandler {
    private static final String MAIN_PROGRAM = "mainProgram";
    private static final String CUSTOMER_PROGRAM = "customerProgram";
    private static final String VOUCHER_PROGRAM = "voucherProgram";
    private final ConsoleView consoleView;
    private final VoucherController voucherController;
    private final CustomerController customerController;

    public MenuHandler(ConsoleView consoleView, VoucherController voucherController, CustomerController customerController) {
        this.consoleView = consoleView;
        this.voucherController = voucherController;
        this.customerController = customerController;
    }

    public MenuType selectCommand(String program){
        try {
            switch (program){
                case MAIN_PROGRAM -> consoleView.printCommand();
                case CUSTOMER_PROGRAM -> consoleView.printCustomerCommand();
                case VOUCHER_PROGRAM -> consoleView.printVoucherCommand();
            }
            return MenuType.of(consoleView.inputCommand());
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void exit(){
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

            VoucherRequest voucherRequest = new VoucherRequest(discount, voucherType);
            consoleView.printSystemMessage(voucherController.createVoucher(voucherRequest).getMessage());
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void createCustomer(){
        try {
            consoleView.printSystemMessage("회원의 이름을 입력해주세요.");
            consoleView.printPrompt();
            String customerName = consoleView.inputCustomerName();

            consoleView.printSystemMessage("회원 타입(NORMAL,BLACKLIST)을 입력해주세요.");
            consoleView.printPrompt();
            CustomerType customerType = CustomerType.of(consoleView.inputCustomerType());

            CustomerRequest customerRequest = new CustomerRequest(customerName, customerType);
            consoleView.printSystemMessage(customerController.createCustomer(customerRequest).getMessage());
        }catch (ErrorMessage e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void voucherList(){
        Response<List<VoucherResponse>> vouchers = voucherController.findAllVouchers();

        List<VoucherResponse> data = vouchers.getData();
        data.forEach(voucher -> consoleView.printInfo(voucher.getInfo()));
    }

    public void customerBlacklist() {
        Response<List<CustomerResponse>> blacklistCustomers = customerController.findAllBlacklistCustomers();

        List<CustomerResponse> result = blacklistCustomers.getData();
        result.forEach(customer -> consoleView.printInfo(customer.getInfo()));
    }

    public void customerList() {
        Response<List<CustomerResponse>> customers = customerController.findAllCustomers();

        List<CustomerResponse> result = customers.getData();
        result.forEach(customer -> consoleView.printInfo(customer.getInfo()));
    }

    public void registerVoucher() {
        try {
            consoleView.printSystemMessage("바우처를 부여할 회원 번호를 입력하세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUUID());

            consoleView.printSystemMessage("등록할 바우처의 번호를 입력하세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUUID());

            consoleView.printSystemMessage(voucherController.registerVoucher(customerId, voucherId).getMessage());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void removeVoucherFromCustomer() {
        try {
            consoleView.printSystemMessage("바우처를 제거할 회원 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUUID());
            customerController.findCustomerById(customerId);

            consoleView.printSystemMessage("제거할 바우처의 번호를 입력하세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUUID());

            consoleView.printSystemMessage(voucherController.removeVoucher(voucherId).getMessage());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void customerVoucherList() {
        try {
            consoleView.printSystemMessage("소유하고 있는 바우처 목록을 조회할 회원 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUUID());
            customerController.findCustomerById(customerId);

            Response<List<VoucherResponse>> result = voucherController.findHaveVouchers(customerId);
            List<VoucherResponse> data = result.getData();
            data.forEach(customer -> consoleView.printInfo(customer.getInfo()));
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void findVoucherOwner(){
        try {
            consoleView.printSystemMessage("소유자를 찾고자 하는 바우처 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUUID());

            Response<UUID> data = voucherController.findVoucherOwnerId(voucherId);
            UUID voucherOwnerId = data.getData();
            Response<CustomerResponse> voucherOwner = customerController.findCustomerById(voucherOwnerId);
            consoleView.printSystemMessage(voucherOwner.getData().getInfo());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void removeVoucherById(){
        try{
            consoleView.printSystemMessage("제거할 바우처의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUUID());

            consoleView.printSystemMessage(voucherController.deleteById(voucherId).getMessage());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void removeAllVouchers(){
        try{
            consoleView.printSystemMessage("전체 바우처를 제거하겠습니다.");
            consoleView.printSystemMessage(voucherController.deleteAll().getMessage());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void findVoucherById(){
        try {
            consoleView.printSystemMessage("조회할 바우처의 아이디를 입력해주세요.");
            consoleView.printPrompt();
            UUID voucherId = UUID.fromString(consoleView.inputUUID());

            Response<VoucherResponse> data = voucherController.findVoucherById(voucherId);
            VoucherResponse result = data.getData();
            consoleView.printSystemMessage(result.getInfo());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void removeCustomerById(){
        try{
            consoleView.printSystemMessage("제거할 회원의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUUID());

            consoleView.printSystemMessage(customerController.deleteCustomerById(customerId).getMessage());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void removeAllCustomers(){
        try{
            consoleView.printSystemMessage("전체 회원을 제거하겠습니다.");
            consoleView.printSystemMessage(customerController.deleteAllCustomers().getMessage());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void findCustomerById(){
        try {
            consoleView.printSystemMessage("조회할 회원의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId = UUID.fromString(consoleView.inputUUID());

            Response<CustomerResponse> data = customerController.findCustomerById(customerId);
            CustomerResponse result = data.getData();
            consoleView.printSystemMessage(result.getInfo());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void updateCustomer(){
        try {
            consoleView.printSystemMessage("수정할 회원의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID customerId  = UUID.fromString(consoleView.inputUUID());

            consoleView.printSystemMessage("이름을 수정하기 위해 새로운 이름을 입력하세요.");
            consoleView.printPrompt();
            String newCustomerName = consoleView.inputCustomerName();

            consoleView.printSystemMessage("회원 타입을 입력하세요.");
            consoleView.printPrompt();
            CustomerType newCustomerType = CustomerType.of(consoleView.inputCustomerType());
            CustomerRequest customerRequest = new CustomerRequest(newCustomerName, newCustomerType);

            Response response = customerController.updateCustomer(customerId, customerRequest);
            consoleView.printSystemMessage(response.getMessage());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }

    public void updateVoucher(){
        try {
            consoleView.printSystemMessage("수정할 바우처의 번호를 입력해주세요.");
            consoleView.printPrompt();
            UUID voucherId  = UUID.fromString(consoleView.inputUUID());

            consoleView.printSystemMessage("바우처 수정을 위해 할인율을 입력해주세요");
            consoleView.printPrompt();
            long newDiscount = consoleView.inputNumber();

            consoleView.printSystemMessage("바우처 수정을 위해 수정할 사용 타입을 입력해주세요");
            consoleView.printPrompt();
            UseStatusType newUseStatusType = UseStatusType.of(consoleView.inputVoucherType());

            VoucherUpdateRequest updateRequest = new VoucherUpdateRequest(newDiscount, newUseStatusType);
            Response response = voucherController.updateVoucher(voucherId, updateRequest);
            consoleView.printSystemMessage(response.getMessage());
        }catch (Exception e){
            throw ErrorMessage.error(e.getMessage());
        }
    }
}
