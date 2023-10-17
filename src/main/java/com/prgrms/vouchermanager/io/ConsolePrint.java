package com.prgrms.vouchermanager.io;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.exception.NotCorrectCommand;
import com.prgrms.vouchermanager.exception.NotCorrectForm;
import com.prgrms.vouchermanager.exception.NotCorrectScope;
import com.prgrms.vouchermanager.message.ConsoleMessage;
import com.prgrms.vouchermanager.message.LogMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
@Slf4j
public class ConsolePrint {

    private final Scanner sc = new Scanner(System.in);

    public Command run() {
        System.out.println(ConsoleMessage.SELECT_FUNCTION.getMessage());

            switch (sc.nextLine()) {
                case "create" -> {
                    log.info(LogMessage.SELECT_CREATE.getMessage());

                    return Command.CREATE;
                }
                case "list" -> {
                    log.info(LogMessage.SELECT_LIST.getMessage());

                    return Command.LIST;
                }
                case "exit" -> {
                    System.out.println(ConsoleMessage.FINISH_PROGRAM.getMessage());

                    return Command.EXIT;
                }
                case "blacklist" -> {
                    log.info(LogMessage.SELECT_BLACKLIST.getMessage());

                    return Command.BLACKLIST;
                }
                default -> throw new NotCorrectCommand();
            }
    }


    public VoucherType getVoucherType() throws NotCorrectForm {
        System.out.println(ConsoleMessage.GET_VOUCHER_TYPE.getMessage());
        String type = sc.nextLine();

        try {
            if (type.equals("fixed")) {
                return VoucherType.FIXED;
            } else if (type.equals("percent")) {
                return VoucherType.PERCENT;
            } else {
                throw new NotCorrectForm();
            }
        } catch (NotCorrectForm e) {
            throw new NotCorrectForm();
        }
    }

    public long getVoucherDiscount(VoucherType type) throws NotCorrectScope, NotCorrectForm {
        long discount = 0;
        try {
            if (type == VoucherType.FIXED) {
                System.out.println(ConsoleMessage.GET_DISCOUNT_AMOUNT.getMessage());
                discount = Long.parseLong(sc.nextLine());
                if(discount < 0) throw new NotCorrectScope();
            } else if (type == VoucherType.PERCENT) {
                System.out.println(ConsoleMessage.GET_DISCOUNT_PERCENT.getMessage());
                discount = Long.parseLong(sc.nextLine());
                if(discount < 0 || discount > 100) throw new NotCorrectScope();
            }
        } catch (NumberFormatException e) {
            throw new NotCorrectForm();
        } catch (NotCorrectScope e) {
            throw new NotCorrectScope();
        }

        return discount;
    }

    public void printCompleteCreate() {
        System.out.println(ConsoleMessage.COMPLETE_CREATE_VOUCHER.getMessage());
        System.out.println();
    }

    public void printList(List<Voucher> vouchers) {
        vouchers.forEach(voucher -> {
                    System.out.println(voucher);
                    System.out.println("---------------");
                });
        System.out.println();
    }

    public void printBlacklist(List<Customer> blacklist) {
        blacklist.forEach(customer -> {
            System.out.println(customer);
            System.out.println("---------------");
        });
        System.out.println();
    }
}
