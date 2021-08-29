package com.programmers.kdtspringorder;

import com.programmers.kdtspringorder.command.Command;
import com.programmers.kdtspringorder.customer.Customer;
import com.programmers.kdtspringorder.customer.CustomerServiceImpl;
import com.programmers.kdtspringorder.io.Input;
import com.programmers.kdtspringorder.io.Output;
import com.programmers.kdtspringorder.voucher.domain.Voucher;
import com.programmers.kdtspringorder.voucher.VoucherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Component
public class CommandLineApplication implements CommandLineRunner {

    private final Input input;
    private final Output output;
    private final VoucherService voucherService;
    private final CustomerServiceImpl customerService;

    public CommandLineApplication(Input input, Output output, VoucherService voucherService, CustomerServiceImpl customerService) {
        this.input = input;
        this.output = output;
        this.voucherService = voucherService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
        output.printMessage("=== Voucher Program ===");
        manageVoucherProcess();
    }

    private void manageVoucherProcess() {
        while (true) {
            printCommand();
            String inputText = input.inputText();

            if (Command.EXIT.toString().equalsIgnoreCase(inputText)) break;

            switch (inputText) {
                case "CREATE" -> createProcess();
                case "VOUCHER" -> voucherProcess();
                case "CUSTOMER" -> customerProcess();
                default -> defaultProcess();
            }
            output.newLine();
        }
    }

    private void customerProcess() {
        List<Customer> customerList = customerService.findAll();
        showCustomers(customerList);
        output.printMessage("바우처를 조회할 고객의 번호를 선택하세요.");
        int index = Integer.parseInt(input.inputText()) - 1;
        UUID customerId = customerList.get(index).getCustomerId();
        List<Voucher> customerVouchers = voucherService.findByCustomerId(customerId);
        if (customerVouchers.isEmpty()) {
            output.printMessage("해당 고객은 소유한 바우처가 없습니다");
            output.printMessage("고객에게 바우처를 할당하려면 1번을 입력해주세요.");
        } else{
            showVouchers(customerVouchers);
            output.printMessage("고객에게 바우처를 할당하려면 1번, 삭제하려면 2번을 입력해주세요.");
        }
        int inputNum = Integer.parseInt(input.inputText());

        switch (inputNum) {
            case 1 -> {
                List<Voucher> allVouchers = voucherService.findAllWithoutCustomerId();
                showVouchers(allVouchers);

                output.printMessage("할당할 바우처의 번호를 선택해주세요");
                int voucherIndex = Integer.parseInt(input.inputText());
                UUID voucherId = allVouchers.get(voucherIndex).getVoucherId();
                voucherService.allocateVoucher(voucherId, customerId);
            }
            case 2 -> {
                output.printMessage("삭제할 바우처의 번호를 선택해주세요");
                int voucherIndex = Integer.parseInt(input.inputText()) - 1;
                UUID voucherId = customerVouchers.get(voucherIndex).getVoucherId();
                voucherService.deallocateVoucher(voucherId);
            }
        }
    }

    private void voucherProcess() {
        List<Voucher> voucherList = voucherService.findAll();
        showVouchers(voucherList);
        output.printMessage("바우처의 번호를 입력하면 소유한 고객의 정보를 확인할 수 있습니다");
        int index = Integer.parseInt(input.inputText()) - 1;
        UUID customerId = voucherList.get(index).getCustomerId();
        if (customerId == null) {
            output.printMessage("보유한 고객이 없습니다.");
            return;
        }
        Customer customer = customerService.findById(customerId).get();
        output.printMessage(customer.toString());
    }

    private void defaultProcess() {
        output.printMessage("잘못 입력 하셨습니다. 입력 가능한 명령어는 exit, create, voucher, customer 입니다.");
    }

    private void createProcess() {
        output.printMessage("2000원 쿠폰 생성은 'FIXED', 10% 쿠폰 생성은 'PERCENT'를 선택해주세요");
        String type = input.inputText();
        if (isWrongType(type)) {
            output.printMessage("잘못 입력 하셨습니다");
        }
        Voucher voucher = createVoucher(type, 20);
        output.printMessage("쿠폰 생성에 성공하였습니다");
    }

    private void printCommand() {
        output.printMessage("Type EXIT to exit the program");
        output.printMessage("Type CREATE to create a new voucher");
        output.printMessage("Type VOUCHER to list all vouchers");
        output.printMessage("Type CUSTOMER to list all vouchers");
    }

    private Voucher createVoucher(String type, long value) {
        return voucherService.createVoucher(type, value);
    }

    private boolean isWrongType(String type) {
        if (type == null) return false;
        return !("FIXED".equalsIgnoreCase(type) || "PERCENT".equalsIgnoreCase(type));
    }

    private void showCustomers(List<Customer> customerList) {
        for (int i = 0; i < customerList.size(); i++) {
            System.out.println(MessageFormat.format("{0}. {1}", (i + 1), customerList.get(i).toString()));
        }
    }

    private void showVouchers(List<Voucher> voucherList) {
        for (int i = 0; i < voucherList.size(); i++) {
            System.out.println(MessageFormat.format("{0}. {1}", (i + 1), voucherList.get(i).toString()));
        }

    }
}
