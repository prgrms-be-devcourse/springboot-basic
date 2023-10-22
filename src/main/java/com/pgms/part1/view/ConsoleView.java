package com.pgms.part1.view;

import com.pgms.part1.domain.customer.dto.CustomerResponseDto;
import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherMenuRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleView {
    private final TextIO textIO = TextIoFactory.getTextIO();

    public VoucherMenuRequestDto init(){
        String command = textIO.newStringInputReader()
                .read("""
                         
                         === Voucher Program ===
                        Type **exit** to exit the program.
                        Type **create** to create a new voucher.
                        Type **list** to list all vouchers.
                        Type **blacklist** to list blocked customers.
                        """);

        return new VoucherMenuRequestDto(command);
    }

    public VoucherCreateRequestDto createVoucher(){
        Integer command = textIO.newIntInputReader()
                .read("""
                         
                         === Select Voucher ===
                        1. FixedAmountVoucher
                        2. PercentDiscountVoucher
                        """);

        Integer discount = textIO.newIntInputReader()
                .read("""
                        
                        Enter Discount Amount
                        """);

        return new VoucherCreateRequestDto(command, discount);
    }

    public void listVoucher(List<VoucherResponseDto> voucherResponseDtos){
        System.out.println("\n === Voucher List ===");
        voucherResponseDtos.stream().forEach(v ->
            System.out.println("ID: " + v.id() + " | Voucher Type: "
                    + v.voucherDiscountType().getDiscountType() + " | " + "Discount " + v.voucherDiscountType().getCalculateType() + ": " + v.discount())
        );
    }

    public void listBlockedCustomers(List<CustomerResponseDto> customerResponseDtos){
        System.out.println("\n === Customer Black List ===");
        customerResponseDtos.stream().forEach(c ->
                System.out.println("ID: " + c.id() + " | isBlocked: " + c.isBlocked())
        );
    }

    public void error(Exception e){
        System.out.println("\n" + e.getMessage());
    }
}
