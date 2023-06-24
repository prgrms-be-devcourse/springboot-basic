package com.prgrms.controller;

import com.prgrms.io.Input;
import com.prgrms.io.Menu;
import com.prgrms.io.Output;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherPolicy;
import com.prgrms.repository.voucher.VoucherRepository;
import com.prgrms.service.voucher.VoucherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VoucherController implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Input input = new Input();
    private static Output output = new Output();
    private final VoucherService voucherService;
    private final VoucherRepository voucherRepository;

    @Override
    public void run(String... args) {
        boolean isRunning = true;

        while (isRunning) {
            output.viewStartMessage();
            try {
                switch (getMenuType()) {
                    case EXIT:
                        isRunning=false;
                        exit();
                        break;
                    case CREATE:
                        create();
                        break;
                    case LIST:
                        list();
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }catch (IllegalArgumentException e){
                logger.error("사용자의 잘못된 입력이 발생하였습니다.");
                output.viewInputError();
            }
        }
    }

    public Menu getMenuType(){
        return input.enterMenu().orElseThrow(() -> new IllegalArgumentException());
    }
    public void exit() {
        output.viewEndMessage();
    }

    public void create() {
        output.viewVoucherOption();
        input.enterVoucherPolicy()
                .ifPresentOrElse(
                        voucherPolicy -> create(voucherPolicy),
                        () -> {
                            throw new IllegalArgumentException();
                        });

    }

    public void create(VoucherPolicy voucherPolicy) {
        output.viewDiscountGuide(voucherPolicy);

        long discount = input.enterDiscount();
        Voucher voucher = voucherService.createVoucher(voucherPolicy, discount);
        voucherRepository.insert(voucher);

        output.viewCompleteVoucher();
    }

    public void list() {
        List<Voucher> voucherList = voucherRepository.getAllVoucherList();
        if (voucherService.isEmptyRepository(voucherList)) {
            output.viewEmptyRepository();
        }
        output.viewVoucherList(voucherList);
    }
}
