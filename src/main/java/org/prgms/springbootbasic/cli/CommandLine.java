package org.prgms.springbootbasic.cli;

import org.prgms.springbootbasic.domain.customer.Customer;
import org.prgms.springbootbasic.domain.customer.CustomerCreateDTO;
import org.prgms.springbootbasic.domain.customer.CustomerDTO;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherCreateDTO;
import org.prgms.springbootbasic.domain.voucher.VoucherDTO;
import org.prgms.springbootbasic.domain.voucher.VoucherType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CommandLine {
    private final Input input;
    private final Output output;

    public CommandLine(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public String getMainCommand() {
        this.output.print("=== Voucher Program ===");
        this.output.print(Command.getMessages());
        return this.input.read();
    }

    public VoucherCreateDTO getVoucherCommand() {
        this.output.print("choose type of voucher (fixed or percent)");
        VoucherType voucherType = VoucherType.findVoucherType(this.input.read());
        long amount = 0L;

        if (voucherType == VoucherType.FIXED) {
            this.output.print("enter amount of voucher (0 ~)");
            amount = Long.parseLong(this.input.read());
        } else if (voucherType == VoucherType.PERCENT) {
            this.output.print("enter percent of voucher (0 ~ 100)");
            amount = Long.parseLong(this.input.read());
        }

        return new VoucherCreateDTO(voucherType, amount);
    }

    public CustomerCreateDTO getCustomerCommand() {
        this.output.print("Type name of customer");
        String name = this.input.read();
        this.output.print("Type email of customer");
        String email = this.input.read();

        return new CustomerCreateDTO(name, email);
    }

    public UUID getManageVoucherCommand() {
        this.output.print("Type customer id to find vouchers belonging to the customer");
        String customerId = this.input.read();
        if (this.input.isUUID(customerId)) {
            return UUID.fromString(customerId);
        } else {
            throw new IllegalArgumentException("not valid uuid entered");
        }
    }

    public boolean getYesOrNoCommand(String description) {
        this.output.print("Type yes (if not, type no)\t" + description);
        String inputYesOrNo = this.input.read();
        if ("yes".equalsIgnoreCase(inputYesOrNo)) {
            return true;
        } else if ("no".equalsIgnoreCase(inputYesOrNo)) {
            return false;
        } else {
            throw new IllegalArgumentException("not valid yes or no command");
        }
    }

    public UUID getVoucherCommand(String description) {
        this.output.print("Type voucher id\t"+ description);
        String voucherId = this.input.read();
        if (this.input.isUUID(voucherId)) {
            return UUID.fromString(voucherId);
        } else {
            throw new IllegalArgumentException("not valid uuid entered");
        }
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
                List<VoucherDTO> voucherDTOS =
                        list.stream().map(v -> this.convertToVoucherDTO((Voucher) v)).toList();

                for (VoucherDTO item : voucherDTOS) {
                    output.print(item.toString());
                }
            } else if (list.get(0) instanceof Customer){
                List<CustomerDTO> customerDTOS =
                        list.stream().map(c -> this.convertToCustomerDTO((Customer) c)).toList();

                for (CustomerDTO item : customerDTOS) {
                    output.print(item.toString());
                }
            }

            else {
                for (Object item : list) {
                    output.print(item.toString());
                }
            }
        }

    }

    private VoucherDTO convertToVoucherDTO(Voucher voucher) {
        return new VoucherDTO(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getAmount());
    }

    private CustomerDTO convertToCustomerDTO(Customer customer) {
        return new CustomerDTO(customer.getCustomerId(), customer.getEmail(), customer.getName());
    }

}
