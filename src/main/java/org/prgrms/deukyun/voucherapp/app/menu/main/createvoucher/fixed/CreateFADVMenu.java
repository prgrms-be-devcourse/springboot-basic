package org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.fixed;

import org.prgrms.deukyun.voucherapp.app.menu.Menu;
import org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.CreateVoucherMenuChoice;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.FixedAmountDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 정액 할인 바우처 생성 메뉴
 */
@Component
public class CreateFADVMenu extends Menu<CreateVoucherMenuChoice> {

    private final VoucherService voucherService;
    private final Scanner scanner;

    public CreateFADVMenu(VoucherService voucherService) {
        super(CreateVoucherMenuChoice.FIXED);
        this.voucherService = voucherService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * 할인액을 입력받아
     * 바우처 서비스를 통해 정액 할인 바우처 생성
     */
    @Override
    public void proc() {
        long amount;
        try{
            amount = scanner.nextLong();
        }catch (InputMismatchException ex){
            throw new IllegalArgumentException("amount must be positive number.");
        }
        voucherService.insert(new FixedAmountDiscountVoucher(amount));
    }
}
