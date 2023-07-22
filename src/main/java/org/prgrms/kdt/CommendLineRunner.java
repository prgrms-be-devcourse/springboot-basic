package org.prgrms.kdt;

import org.prgrms.kdt.commendLine.Console;
import org.prgrms.kdt.exception.InvalidInputException;
import org.prgrms.kdt.member.controller.MemberController;
import org.prgrms.kdt.util.Menu;
import org.prgrms.kdt.voucher.controller.VoucherController;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.dto.CreateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CommendLineRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommendLineRunner.class);
    private final VoucherController voucherController;
    private final MemberController memberController;
    private final Console console;

    public CommendLineRunner(VoucherController voucherController, MemberController memberController, Console console) {
        this.voucherController = voucherController;
        this.memberController = memberController;
        this.console = console;
    }

    @Override
    public void run(String... args) {
        boolean isRunning = true;
        while (isRunning) {
            try {
                console.printMenu();
                String getUserMenu = console.getUserMenu();
                Menu menu = Menu.getMenu(getUserMenu);
                isRunning = menu.isNotExit();
                executeAction(menu);

            } catch (InvalidInputException e) {
                console.printError();
            } catch (Exception e) {
                logger.error(e.toString());
            }
        }
    }

    // handler mapping
    private void executeAction(Menu menu) throws IOException {
        switch (menu) {
            case CREATE:
                createVoucher();
                break;
            case LIST:
                console.printAllBoucher(voucherController.findAll());
                break;
            case BLACK_LIST:
                console.printAllBlackList(memberController.findAllBlackMember());
                break;
        }
    }

    private void createVoucher() throws IOException {
        VoucherType voucherType = VoucherType.getType(console.getVoucherTypes());
        double discountAmount = Double.parseDouble(console.getDiscountAmount());
        voucherController.create(new CreateRequest(voucherType, discountAmount));
    }
}
