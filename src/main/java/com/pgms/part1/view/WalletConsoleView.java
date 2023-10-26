package com.pgms.part1.view;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import com.pgms.part1.domain.wallet.dto.WalletCreateRequestDto;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WalletConsoleView extends CommonConsoleView{
    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public String getMenu(){
        String command = textIO.newStringInputReader()
                .read("""
                         
                         === Wallet Menu ===
                        Type **add** to create a new voucher.
                        Type **customer list** to list customers own some voucher.
                        Type **voucher list** to list vouchers owned by some customer.
                        Type **delete** to delete voucher.
                        Type **exit** to exit the menu.
                        """);

        return command;
    }


    public WalletCreateRequestDto addWallet() {
        Long customerId = textIO.newLongInputReader()
                .read("""
                         
                         === Add Wallet ===
                        Enter the Customer Id
                        """);

        Long voucherId = textIO.newLongInputReader()
                .read("""
                         
                        Enter the Voucher Id
                        """);

        return new WalletCreateRequestDto(customerId, voucherId);
    }

    public Long deleteWallet() {
        Long id = textIO.newLongInputReader()
                .read("""
                         
                         === Delete Voucher ===
                        Enter the Voucher Id
                        """);
        return id;
    }

    public Long getCustomerId() {
        Long id = textIO.newLongInputReader()
                .read("""
                         
                         === Vouchers List owned by Customer ===
                        Enter the Customer Id
                        """);
        return id;
    }

    public Long getVoucherId() {
        Long id = textIO.newLongInputReader()
                .read("""
                         
                         === Customers List own Voucher ===
                        Enter the Voucher Id
                        """);
        return id;
    }

    public void listVoucher(List<VoucherResponseDto> voucherResponseDtos){
        voucherResponseDtos.stream().forEach(v ->
                System.out.println("ID: " + v.id() + " | Voucher Type: "
                        + v.voucherDiscountType().getDiscountType() + " | " + "Discount " + v.voucherDiscountType().getCalculateType() + ": " + v.discount())
        );
    }

    public void listCustomers(List<CustomerResponseDto> customerResponseDtos){
        customerResponseDtos.stream().forEach(c ->
                System.out.println("ID: " + c.id() + " | Name: " + c.name()
                        + " | Email: " + c.email() + " | isBlocked: " + c.isBlocked())
        );
    }
}
