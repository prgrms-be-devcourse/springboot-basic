package org.prgms.springbootbasic.cli;

import org.prgms.springbootbasic.domain.Voucher;
import org.prgms.springbootbasic.domain.VoucherChoiceDTO;
import org.prgms.springbootbasic.domain.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandLine {
    private final Input input;
    private final Output output;

    public CommandLine(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public String getMainCommand() {
        this.output.print(new String[]{"=== Voucher Program ===",
                "Type exit to exit the program.",
                "Type create to create a new voucher.",
                "Type list to list all vouchers.",
                "Type blacklist to list all blacklisted customer"
        });

        return this.input.read();
    }

    public VoucherChoiceDTO getVoucherCommand() {
        this.output.print("choose type of voucher (fixed / percent)");
        VoucherType voucherType = VoucherType.findVoucherType(this.input.read());
        long amount = 0L;

        if (voucherType.equals(VoucherType.FIXED)) {
            this.output.print("enter amount of voucher (0 ~)");
            amount = Long.parseLong(this.input.read());
        } else if (voucherType.equals(VoucherType.PERCENT)) {
            this.output.print("enter percent of voucher (0 ~ 100)");
            amount = Long.parseLong(this.input.read());
        }

        return new VoucherChoiceDTO(voucherType, amount);
    }

    public boolean stopCommandLine() {
        input.stop();
        output.stop();
        return true;
    }


    public <T> void printList(List<T> list) {
        if (list.isEmpty()) {
            output.print("list is empty");
        } else {
            if (list.get(0) instanceof Voucher) {
                List<VoucherChoiceDTO> voucherChoiceDTOArrayList =
                        list.stream().map(v -> this.convertToVoucherDTO((Voucher) v)).toList();

                for (VoucherChoiceDTO item : voucherChoiceDTOArrayList) {
                    output.print(item.toString());
                }
            } else {
                for (Object item : list) {
                    output.print(item.toString());
                }
            }
        }

    }

    private VoucherChoiceDTO convertToVoucherDTO(Voucher voucher) {
        return new VoucherChoiceDTO(voucher.getVoucherType(), voucher.getAmount());
    }


}
