
package com.pgms.part1.domain.view;

import com.pgms.part1.domain.voucher.dto.VoucherCreateRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherMenuRequestDto;
import com.pgms.part1.domain.voucher.dto.VoucherResponseDto;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConsoleView {
    private TextIO textIO = TextIoFactory.getTextIO();

    public VoucherMenuRequestDto init(){
        String command = textIO.newStringInputReader()
                .read("""
                         === Voucher Program ===
                        Type **exit** to exit the program.
                        Type **create** to create a new voucher.
                        Type **list** to list all vouchers.
                        """);

        return new VoucherMenuRequestDto(command);
    }

    public VoucherCreateRequestDto createVoucher(){
        String command = textIO.newStringInputReader()
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
        System.out.println(" === Voucher List ===");
        voucherResponseDtos.stream().forEach(v ->
            System.out.println("ID. " + v.id() + "  Voucher Type. "
                    + v.voucherDiscountType().getDiscountType() + "  " + v.voucherDiscountType().getCalculateType() + ". " + v.discount())
        );
    }
}
