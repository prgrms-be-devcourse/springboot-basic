package org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.percent;

import org.prgrms.deukyun.voucherapp.app.menu.Menu;
import org.prgrms.deukyun.voucherapp.app.menu.main.createvoucher.CreateVoucherMenuChoice;
import org.prgrms.deukyun.voucherapp.domain.voucher.entity.PercentDiscountVoucher;
import org.prgrms.deukyun.voucherapp.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 정률 할인 바우처 생성 메뉴
 */
@Component
public class CreatePDVMenu extends Menu<CreateVoucherMenuChoice> {

    private final VoucherService voucherService;
    private final Scanner scanner;

    public CreatePDVMenu(VoucherService voucherService) {
        super(CreateVoucherMenuChoice.PERCENT);
        this.voucherService = voucherService;
        scanner = new Scanner(System.in);
    }

    /**
     * 할인률을 입력받아 <br>
     * 바우처 서비스를 통해 정률 할인 바우처 생성
     */
    @Override
    public void proc() {
        long percent;
        try{
            percent = scanner.nextLong();
        }catch (InputMismatchException ex){
            throw new IllegalArgumentException("percent must be between 0 and 100 inclusive.");
        }
        voucherService.insert(new PercentDiscountVoucher(percent));
    }
}
