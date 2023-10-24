package com.prgrms.vouchermanager.domain;

import com.prgrms.vouchermanager.controller.CustomerController;
import com.prgrms.vouchermanager.controller.VoucherController;
import com.prgrms.vouchermanager.exception.*;
import com.prgrms.vouchermanager.io.ConsolePrint;
import com.prgrms.vouchermanager.io.VoucherType;
import com.prgrms.vouchermanager.message.LogMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class VoucherExecutor implements Executor {

    private final ConsolePrint consolePrint;
    private final VoucherController controller;
    private final Scanner sc = new Scanner(System.in);

    public VoucherExecutor(ConsolePrint consolePrint, VoucherController controller) {
        this.consolePrint = consolePrint;
        this.controller = controller;
    }

    public void create() {
        VoucherType voucherType = getVoucherType();
        long discount = getVoucherDiscount(voucherType);

        controller.create(voucherType, discount);
        consolePrint.printCompleteCreate();
    }

    public void list() throws EmptyListException {
        List<Voucher> vouchers = controller.list();
        if(vouchers.isEmpty()) throw new EmptyListException(vouchers);
        else consolePrint.printVoucherList(controller.list());
    }

    public VoucherType getVoucherType() throws NotCorrectForm {
        consolePrint.printGetVoucherType();
        String type = sc.nextLine();

        try {
            if (type.equals("fixed")) {
                return VoucherType.FIXED;
            } else if (type.equals("percent")) {
                return VoucherType.PERCENT;
            } else {
                throw new NotCorrectForm(type);
            }
        } catch (NotCorrectForm e) {
            throw new NotCorrectForm(type);
        }
    }

    private long getVoucherDiscount(VoucherType type) throws NotCorrectScope, NotCorrectForm {
        long discount = 0;
        try {
            if (type == VoucherType.FIXED) {
                discount = getFixedDiscount();
            } else if (type == VoucherType.PERCENT) {
                discount = getPercentDiscount();
            }
        } catch (NumberFormatException e) {
            throw new NotCorrectForm(String.valueOf(discount));
        } catch (NotCorrectScope e) {
            throw new NotCorrectScope(discount);
        }

        return discount;
    }

    private long getPercentDiscount() throws NotCorrectScope {
        long discount;
        consolePrint.printGetDiscountPercent();
        discount = Long.parseLong(sc.nextLine());
        if(discount < 0 || discount > 100) throw new NotCorrectScope(discount);
        return discount;
    }

    private long getFixedDiscount() throws NotCorrectScope {
        long discount;
        consolePrint.printGetDiscountAmount();
        discount = Long.parseLong(sc.nextLine());
        if(discount < 0) throw new NotCorrectScope(discount);
        return discount;
    }
}
