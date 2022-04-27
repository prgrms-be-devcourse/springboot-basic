package org.programmer.kdtspringboot;

import org.programmer.kdtspringboot.user.User;
import org.programmer.kdtspringboot.user.UserService;
import org.programmer.kdtspringboot.voucher.Voucher;
import org.programmer.kdtspringboot.voucher.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class VoucherSystem {

    private final Console console;
    private final VoucherService voucherService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(VoucherSystem.class);

    public VoucherSystem(Console console, VoucherService voucherService, UserService userService) {
        this.console = console;
        this.voucherService = voucherService;
        this.userService = userService;
    }

    public void run() {
        while (true) {
            try {
                console.menu();
                String inputString = console.input("선택: ").toLowerCase();
                switch (inputString) {
                    case "exit":
                        logger.info("exit 입력");
                        console.exit();
                        return;
                    case "list":
                        logger.info("list 입력");
                        showVoucherList();
                        break;
                    case "create":
                        logger.info("create 입력");
                        createVoucher();
                        break;
                    case "blacklist":
                        logger.info("blacklist 입력");
                        showBlackUserList();
                    default:
                        logger.warn("메뉴 잘못 입력");
                        console.print("제대로 입력해주세요.");
                }
            } catch (IOException e) {
                logger.error("입출력 오류");
            } catch (NumberFormatException e) {
                logger.error("할인값을 제대로 입력해주세요");
            }
        }
    }

    private void showBlackUserList() throws IOException {
        List<User> list = userService.findAllUsers();
        list.stream().map(Object::toString).forEach(console::print);
    }

    private void showVoucherList() throws IOException {
        List<Voucher> list = voucherService.findAllVouchers();
        list.stream().map(Object::toString).forEach(console::print);
    }

    private void createVoucher() {
        console.choice();

        String inputString = console.input("선택: ");
        logger.info("Voucher 종류 선택: " + inputString);
        Long discount = Long.parseLong(console.input("할인값: "));

        logger.info("할인값 입력: " + discount);
        voucherService.createVoucher(inputString, discount);
    }
}
