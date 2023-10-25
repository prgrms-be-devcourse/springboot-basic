package com.prgrms.vouchermanager.handler.executor;

import com.prgrms.vouchermanager.controller.VoucherController;
import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
import com.prgrms.vouchermanager.exception.EmptyListException;
import com.prgrms.vouchermanager.exception.NotCorrectForm;
import com.prgrms.vouchermanager.exception.NotCorrectId;
import com.prgrms.vouchermanager.exception.NotCorrectScope;
import com.prgrms.vouchermanager.io.ConsolePrint;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@Component
public class VoucherExecutor {

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

    public void delete() {
        consolePrint.printGetVoucherId();
        try {
            UUID id = UUID.fromString(sc.nextLine());
            controller.delete(id);
        } catch (IllegalArgumentException e) {
            throw new NotCorrectId();
        }
        consolePrint.printCompleteDelete();
    }

    public void update() {
        consolePrint.printGetVoucherId();
        UUID id = UUID.fromString(sc.nextLine());
        consolePrint.printGetCustomerYear();
        int discount = 0;
        try {
            discount = sc.nextInt();
            sc.nextLine();
            controller.updateDiscount(id, discount);
        } catch (NumberFormatException e) {
            throw new NotCorrectForm(String.valueOf(discount));
        }
        consolePrint.printCompleteUpdate();
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
