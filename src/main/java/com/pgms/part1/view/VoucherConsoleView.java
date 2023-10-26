package com.pgms.part1.view;

import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherConsoleView extends CommonConsoleView{
    private final TextIO textIO = TextIoFactory.getTextIO();

    @Override
    public String getMenu(){
        String command = textIO.newStringInputReader()
                .read("""
                         
                         === Voucher Menu ===
                        Type **create** to create a new voucher.
                        Type **list** to list all vouchers.
                        Type **exit** to exit the menu.
                        """);

        return command;
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

}
