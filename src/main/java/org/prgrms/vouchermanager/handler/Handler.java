//package org.prgrms.vouchermanager.handler;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.prgrms.vouchermanager.domain.customer.Customer;
//import org.prgrms.vouchermanager.domain.customer.CustomerRequest;
//import org.prgrms.vouchermanager.domain.voucher.MenuType;
//import org.prgrms.vouchermanager.domain.voucher.Voucher;
//import org.prgrms.vouchermanager.domain.wallet.WalletRequestDto;
//import org.prgrms.vouchermanager.exception.InputValueException;
//import org.prgrms.vouchermanager.exception.NotExistEmailException;
//import org.prgrms.vouchermanager.exception.NotExistVoucherException;
//import org.prgrms.vouchermanager.io.Input;
//import org.prgrms.vouchermanager.io.Output;
//import org.prgrms.vouchermanager.util.UuidUtil;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Slf4j
//@Component
//public class Handler {
//    private final Input input;
//    private final Output output;
//
//    private final WebVoucherController voucherController;
//    private final WebCustomerController customerController;
//    private final ApiWalletController walletController;
//    private boolean continueOrNot = true;
//    public void init() throws IOException {
//        while(continueOrNot){
//            output.selectMenu();
//            String menu = input.selectMenu();
//            switch (menu){
//                case "1" : voucherLauncher();
//                break;
//                case "2" : customerLauncher();
//                break;
//                case "3" : walletLauncher();
//                default: log.error("입력 값 : {}", menu + " (1과 2중 선택해야 합니다)");
//            }
//        }
//    }
//
//    private void voucherLauncher(){
//        output.voucherInit();
//
//        try{
//            String voucherMenu = input.voucherInit();
//            MenuType menu = MenuType.fromValue(voucherMenu);
//            if(menu == MenuType.CREATE)
//                voucherCreate();
//            else if(menu == MenuType.LIST)
//                voucherList();
//            else if(menu == MenuType.EXIT)
//                continueOrNot = false;
//        }catch (IllegalArgumentException e){
//            output.print(e.getMessage());
//        }
//        catch (InputValueException e){
//            log.error(e.getMessage() + "(create or list or exit)");
//        }catch (IOException e) {
//            log.error(e.getMessage());
//        }
//    }
//
//    private void voucherList() {
//        List<Voucher> allVouchers = voucherController.findAllVoucher();
//        allVouchers.forEach(voucher -> output.print(voucher.toString()));
//    }
//
//    private void voucherCreate(){
//        output.createVoucherMenu();
//        try{
//            String createType = input.createVoucher();
//            MenuType menuType = MenuType.fromValue(createType);
//            voucherController.create(menuType);
//        }catch (InputValueException e){
//            log.error(e.getMessage() + "(Fixed or Percent)");
//        }catch (IOException e){
//            log.error(e.getMessage());
//        }
//    }
//
//
//    private void customerLauncher(){
//        output.customerInit();
//        try{
//            String customerMenu = input.customerInit();
//            MenuType customerType = MenuType.fromValue(customerMenu);
//            if(customerType == MenuType.LIST){
//                customerList();
//            }
//            else if(customerType == MenuType.CREATE){
//                customerCreate();
//            }
//            else if(customerType == MenuType.EXIT){
//                continueOrNot = false;
//            }
//        }catch (InputValueException e){
//            log.error(e.getMessage() + "(create or list or exit)");
//        }
//        catch (IOException e){
//            log.error(e.getMessage());
//        }
//
//    }
//
//    private void customerCreate() {
//        output.outputCustomerName();
//        try{
//            String name = input.inputCustomerName();
//            output.outputCustomerEmail();
//            String email = input.inputCustomerEmail();
//            output.outputCustomerisBlack();
//            String isBlack = input.inputCustomerisBlack();
//            CustomerRequest requestDto = new CustomerRequest(name, email, toBooleanType(isBlack));
//            customerController.create(requestDto);
//        }catch (IOException e){
//            log.error(e.getMessage());
//        }catch (InputValueException e){
//            log.error(e.getMessage() + "true or false");
//        }
//
//    }
//
//    private void customerList() {
//        List<Customer> all = customerController.list();
//        all.forEach(customer -> output.print(customer.toString()));
//    }
//
//    private boolean toBooleanType(String isBlack){
//        if(isBlack.equals("true")){
//            return true;
//        }
//        else if(isBlack.equals("false")){
//            return false;
//        }
//        else{
//            throw new InputValueException();
//        }
//    }
//    private void walletLauncher() {
//        output.walletInit();
//        try{
//            String menu = input.selectMenu();
//            MenuType menuType = MenuType.fromValue(menu);
//            if(menuType == MenuType.CREATE){
//                walletCreate();
//            }
//            else if(menuType == MenuType.EXIT){
//                continueOrNot = false;
//                return;
//            }
//            else if (menuType == MenuType.FIND) {
//                walletFind();
//            }
//            else if(menuType == MenuType.REMOVE){
//                walletRemove();
//            }
//        }catch (IOException e){
//            log.error(e.getMessage());
//        }
//    }
//
//    private void walletRemove() {
//        output.outputWalletRemove();
//        try{
//            String email = input.inputCustomerEmail();
//            walletController.deleteByEmail(email);
//        }catch (IOException | NotExistEmailException e){
//            log.error(e.getMessage());
//        }
//    }
//
//    private void walletFind() {
//        output.outputFindWithMenu();
//        try{
//            String menu = input.createVoucher();
//            MenuType menuType = MenuType.fromValue(menu);
//            if(menuType == MenuType.EMAIL){
//                walletFindByEmail();
//            }
//            else if(menuType == MenuType.VOUCHER){
//                walletFindByVoucher();
//            }
//        }catch (IOException e){
//            log.error(e.getMessage());
//        }catch (InputValueException e){
//            log.error(e.getMessage() + "(email or voucher)");
//        }
//    }
//    private void walletCreate() {
//        try{
//            output.outputWalletEmail();
//            String email = input.inputCustomerEmail();
//            output.outputVoucherId();
//            String  voucherId = input.inputWalletVoucher();
//            Optional<Voucher> voucher = voucherController.findById(UuidUtil.stringToUUID(voucherId));
//            WalletRequestDto dto = WalletRequestDto.builder().customerEmail(email).voucher(voucher.get()).build();
//            walletController.createWallet(dto);
//        }catch (IOException | NotExistEmailException e){
//            log.error(e.getMessage());
//        }
//
//    }
//
//    private void walletFindByEmail() {
//        output.outputWalletEmail();
//        try{
//            String email = input.inputCustomerEmail();
//            walletController.findByEmail(email).orElseThrow(NotExistEmailException::new);
//        }catch (IOException | NotExistEmailException e){
//            log.error(e.getMessage());
//        }
//    }
//    private void walletFindByVoucher() {
//        output.outputWalletVoucher();
//        try{
//            UUID voucherId = UuidUtil.stringToUUID(input.inputWalletVoucher());
//            Optional<Voucher> voucher = voucherController.findById(voucherId);
//            walletController.findByVoucher(voucher.get());
//        }catch (IOException | NotExistVoucherException e){
//            log.error(e.getMessage());
//        }
//    }
//}
