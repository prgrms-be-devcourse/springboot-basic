package org.prgms.order;

import org.prgms.order.customer.entity.Customer;
import org.prgms.order.customer.service.CustomerService;
import org.prgms.order.io.Input;
import org.prgms.order.io.Output;
import org.prgms.order.voucher.entity.*;
import org.prgms.order.voucher.service.VoucherService;
import org.prgms.order.voucher.entity.VoucherCreateStretage;
import org.prgms.order.wallet.Wallet;
import org.prgms.order.wallet.WalletData;
import org.prgms.order.wallet.WalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class CommandLineApplication implements Runnable{
    private static final Logger logger = LoggerFactory.getLogger(CommandLineApplication.class);
    private Input input;
    private Output output;

    private VoucherService voucherService;
    private Voucher voucher;
    private VoucherCreateStretage voucherCreateStretage;

    private CustomerService customerService;
    private WalletService walletService;


    public CommandLineApplication(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = applicationContext.getBean(VoucherService.class);
        customerService = applicationContext.getBean(CustomerService.class);
        walletService = applicationContext.getBean(WalletService.class);
        voucherCreateStretage = applicationContext.getBean(VoucherCreateStretage.class);


        while(true){
            output.mainMenu();
            String inputString = input.input();
            switch (inputString) {
                case "voucher" -> voucherMenu();
                case "customer" -> customerMenu();
                case "wallet" -> walletMenu();
                case "exit" -> {
                    System.out.println("=== Exit Program ===\n");
                    return;
                }
                default -> System.out.println("= RETRY =");
            }
        }
    }

    private void voucherMenu(){
        output.voucherMenu();
        String inputString = input.input();
        switch (inputString) {
            case "create" -> createVoucher();
            case "list" -> allVoucherList();
            case "expiry" -> setVoucherExpiryDate();
            default -> System.out.println("= RETRY =");
        }
    }

    private void createVoucher() {
        output.createVoucherType();
        String inputVoucherString = input.input();
        if(isWrongType(inputVoucherString)){
            System.out.println("=  Insert Correct Type. PLEASE RETRY =");
            return;
        }
        System.out.print("Insert Discount Amount :: ");
        String inputAmount = input.input();

        if(isNotDigit(inputAmount)) {
            System.out.println("=  Insert Correct Amount. PLEASE RETRY =");
            return;
        }
        voucher = voucherCreateStretage.createVoucher(
                VoucherIndexType.valueOf(inputVoucherString.toUpperCase(Locale.ROOT)),
                new VoucherData(UUID.randomUUID(),Long.parseLong(inputAmount),LocalDateTime.now().withNano(0), LocalDateTime.now().withNano(0)));
        voucherService.insert(voucher);
        System.out.println("SUCCESS >>> "+MessageFormat.format("{0}, VoucherId = {1}, Discount = {2}",
                voucher.getType(), voucher.getVoucherId(), voucher.getAmount()));
    }

    private boolean isWrongType(String inputVoucherString) {
        return Arrays.stream(VoucherIndexType.values()).
                noneMatch((value) ->
                        value.name().equals(inputVoucherString.toUpperCase(Locale.ROOT)));
    }
    Pattern numberPattern = Pattern.compile("^[0-9]*$");
    private boolean isNotDigit(String input) {
        return !(numberPattern.matcher(input).matches() && !input.isEmpty());
    }

    private void allVoucherList() {
        System.out.println("\n=== Vouchers ===");

        var voucherList = voucherService.findAll();
        for(int i=0;i<voucherList.size();i++){
            System.out.println(MessageFormat.format("{0} : {1}, VoucherId = {2}, Discount = {3}",
                    i, voucherList.get(i).getType(), voucherList.get(i).getVoucherId(), voucherList.get(i).getAmount()));
        }

        output.deleteVoucher();
        String delete = input.input();
        switch (delete) {
            case "main" -> {}
            case "all" -> voucherService.deleteAll();
            case "choose" -> deleteVoucherByIndex();
            default -> System.out.println("= RETRY =");
        }
    }


    private void deleteVoucherByIndex() {
        var voucherList = voucherService.findAll();

        System.out.print("\nInsert Index ::");
        String index = input.input();
        voucherService.deleteById(voucherList.get(Integer.parseInt(index)).getVoucherId());
    }

    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private void setVoucherExpiryDate() {
        System.out.println("\n=== Set Expiry Date Voucher ===");

        List<Voucher> vouchers = voucherService.findAll();
        for(int i=0;i<vouchers.size();i++){
            System.out.println(MessageFormat.format("{0} : {1}, VoucherId = {2}, Discount = {3}",
                    i, vouchers.get(i).getType(), vouchers.get(i).getVoucherId(), vouchers.get(i).getAmount()));
        }

        System.out.print("Insert Voucher Index ::");
        String voucherIndex = input.input();

        Voucher voucher = vouchers.get(Integer.parseInt(voucherIndex));

        System.out.print("Insert Voucher Index (ex> 2000-01-30 12:00:00)::");
        String expiryDate = input.input();
        LocalDateTime localDateTime = LocalDateTime.parse(expiryDate.substring(0, 19), formatter);

        voucherService.updateExpiryDate(voucher.getVoucherId(), localDateTime.withNano(0));
    }


    private void customerMenu() {
        output.customerMenu();
        String inputString = input.input();
        switch (inputString) {
            case "create" -> createCustomer();
            case "list" -> allCustomerList();
            case "blackList" -> allBlackList();
            default -> System.out.println("= RETRY =");
        }
    }


    private void createCustomer() {
        System.out.println("\n=== create Customer ===");
        System.out.println("Insert Email ::");
        String email = input.input();

        if(customerService.findByEmail(email).isPresent()){
            System.out.println("= Duplicate Email. =");
            return;
        }

        System.out.println("Insert Name ::");
        String name = input.input();

        customerService.createCustomer(email,name);
    }
    private void allCustomerList() {
        System.out.println("\n=== Customers ===");

        customerService.findAllCustomer().forEach((customer) ->
                System.out.println(MessageFormat.format("{0} {1} {2} {3}",
                        customer.getCustomerId(), customer.getEmail(), customer.getName(), customer.getCreatedAt()))
        );

    }
    private void allBlackList() {
        System.out.println("\n=== BlackList ===");
        System.out.println("            CustomerId                name         email");

        customerService.findAllBlackList().forEach((customer) ->
                System.out.println(MessageFormat.format("{0} {1} {2} {3}",
                        customer.getCustomerId(), customer.getEmail(), customer.getName()))
        );
    }

    private void walletMenu() {
        output.walletMenu();
        String inputString = input.input();
        switch (inputString) {
            case "create" -> createWallet();
            case "customer" -> customerWalletList();
            case "voucher" -> voucherWalletList();
            default -> System.out.println("= RETRY =");
        }
    }

    private void createWallet() {
        System.out.print("Insert Customer Email ::");
        String email = input.input();

        Optional<Customer> customer = customerService.findByEmail(email);
        if(customer.isEmpty()){
            System.out.println("= Not Exist Email. =");
            return;
        }

        List<Voucher> vouchers = voucherService.findAll();
        for(int i=0;i<vouchers.size();i++){
            System.out.println(MessageFormat.format("{0} : {1}, VoucherId = {2}, Discount = {3}",
                    i, vouchers.get(i).getType(), vouchers.get(i).getVoucherId(), vouchers.get(i).getAmount()));
        }

        System.out.print("Insert Voucher Index ::");
        String voucherIndex = input.input();

        Voucher voucher = vouchers.get(Integer.parseInt(voucherIndex));
        if(isNotAvailable(voucher)){
            System.out.println("= Expired This Voucher. =");
            return;
        }

        Wallet wallet = walletService.insert(
                new Wallet(new WalletData(
                        UUID.randomUUID(),
                        customer.get().getCustomerId(),
                        voucher.getVoucherId(),
                        LocalDateTime.now())));

        System.out.println("SUCCESS >>> "+MessageFormat.format("{0}, CustomerId = {1}, VoucherId = {2}",
                wallet.getWalletId(), wallet.getCustomerId(), wallet.getVoucherId()));

    }

    private boolean isNotAvailable(Voucher voucher) {
        return voucherService.findAvailableById(voucher.getVoucherId());
    }

    private void voucherWalletList() {
        List<Voucher> vouchers = voucherService.findAll();
        for(int i=0;i<vouchers.size();i++){
            System.out.println(MessageFormat.format("{0} : {1}, VoucherId = {2}, Discount = {3}",
                    i, vouchers.get(i).getType(), vouchers.get(i).getVoucherId(), vouchers.get(i).getAmount()));
        }

        System.out.print("Insert Voucher Index ::");
        String voucherIndex = input.input();

        Voucher voucher = vouchers.get(Integer.parseInt(voucherIndex));

        var walletList = walletService.findByVoucherId(voucher.getVoucherId());

        System.out.println("\n=== Voucher Wallet ===");
        walletList.forEach((w) ->
                System.out.println(MessageFormat.format("{0}, CustomerId = {1}, VoucherId = {2}, , CreatedAt = {3}",
                        w.getWalletId(), w.getCustomerId(), w.getVoucherId(), w.getCreatedAt())));
    }

    private void customerWalletList() {
        System.out.print("\nInsert Email ::");
        String email = input.input();

        if(customerService.findByEmail(email).isEmpty()){
            System.out.println("= Not Exist Email. =");
            return;
        }

        var customer = customerService.findByEmail(email).get();
        var walletList = walletService.findByCustomerId(customer.getCustomerId());

        if(walletList.isEmpty()){
            return;
        }

        System.out.println("\n=== Customer Wallet ===");
        for(int i=0;i<walletList.size();i++){
            System.out.println(MessageFormat.format("{0} : {1}, CustomerId = {2}, VoucherId = {3}, , CreatedAt = {4}",
                    i, walletList.get(i).getWalletId(), walletList.get(i).getCustomerId(), walletList.get(i).getVoucherId(), walletList.get(i).getCreatedAt()));
        }


        output.deleteWallet();
        String delete = input.input();
        switch (delete) {
            case "main" -> {}
            case "all" -> walletService.deleteByCustomerId(customer.getCustomerId());
            case "choose" -> deleteWalletsByIndex(customer);
            default -> System.out.println("= RETRY =");
        }

    }

    private void deleteWalletsByIndex(Customer customer) {
        var walletList = walletService.findByCustomerId(customer.getCustomerId());

        System.out.print("\nInsert Index ::");
        String index = input.input();
        walletService.deleteByWalletId(walletList.get(Integer.parseInt(index)).getWalletId());
    }



}
