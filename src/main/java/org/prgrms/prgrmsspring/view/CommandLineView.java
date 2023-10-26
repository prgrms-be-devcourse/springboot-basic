package org.prgrms.prgrmsspring.view;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import org.prgrms.prgrmsspring.domain.FunctionType;
import org.prgrms.prgrmsspring.domain.VoucherType;
import org.prgrms.prgrmsspring.entity.user.Customer;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class CommandLineView {

    private final TextIO textIO = TextIoFactory.getTextIO();
    private final TextTerminal<?> textTerminal = textIO.getTextTerminal();

    public FunctionType getModeNumber() {
        textTerminal.println("=== Voucher Program ===");
        for (FunctionType functionType : FunctionType.values()) {
            textTerminal.printf("Input %d to use Function %s\n", functionType.getModeNumber(), functionType.name());
        }
        return FunctionType.from(inputModeNumber());
    }

    public <T> void printAll(List<T> list) {
        list.forEach(l -> textTerminal.println(l.toString()));
    }


    private VoucherType inputVoucherType() {
        Arrays.stream(VoucherType.values()).forEach(v -> textTerminal.println("%d. %s".formatted((v.ordinal() + 1), v.getTitle())));
        Integer voucherModeNum = textIO.newIntInputReader().read("Input voucher mode number");
        return VoucherType.from(voucherModeNum);
    }

    public Long inputVoucherAmount() {
        return textIO.newLongInputReader().read("Input voucher amount");
    }

    public UUID inputVoucherId() {
        return UUID.fromString(textIO.newStringInputReader().read("Input voucherId"));
    }

    public Voucher createVoucher() {
        VoucherType voucherType = inputVoucherType();
        Long amount = inputVoucherAmount();
        return voucherType.constructVoucher(UUID.randomUUID(), amount);
    }

    public Voucher updateVoucher() {
        UUID voucherId = inputVoucherId();
        VoucherType voucherType = inputVoucherType();
        Long amount = inputVoucherAmount();
        return voucherType.constructVoucher(voucherId, amount);
    }

    public UUID deleteVoucher() {
        return inputVoucherId();
    }

    public UUID inputCustomerId() {
        return UUID.fromString(textIO.newStringInputReader().read("Input customerId"));
    }

    public String inputCustomerName() {
        return textIO.newStringInputReader().read("Input Customer Name");
    }

    public Boolean inputCustomerIsBlack() {
        String yn = textIO.newStringInputReader().read("Do you want to set BLACK ? ( Y / N )");
        return switch (yn) {
            case "Y", "y" -> true;
            case "N", "n" -> false;
            default -> throw new IllegalArgumentException("Y 또는 N를 입력해주세요.");
        };
    }

    public String inputCustomerEmail() {
        return textIO.newStringInputReader().read("Input Customer Email");
    }

    public Customer createCustomer() {
        String customerName = inputCustomerName();
        Boolean isBlack = inputCustomerIsBlack();
        String email = inputCustomerEmail();
        return new Customer(UUID.randomUUID(), customerName, isBlack, email);
    }

    public Customer updateCustomer() {
        UUID customerId = inputCustomerId();
        String email = inputCustomerEmail();
        String customerName = inputCustomerName();
        Boolean isBlack = inputCustomerIsBlack();
        return new Customer(customerId, customerName, isBlack, email);
    }

    public UUID deleteCustomer() {
        return inputCustomerId();
    }


    public String inputCommand() {
        return textIO.newStringInputReader().read();
    }

    public int inputModeNumber() {
        return textIO.newIntInputReader().read();
    }

    public <T> void print(T obj) {
        textTerminal.println(obj.toString());
    }

}
