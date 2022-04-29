package org.programmers.kdtspring.ConsoleIO;

import org.programmers.kdtspring.service.BlackListUserService;
import org.programmers.kdtspring.service.CustomerService;
import org.programmers.kdtspring.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ConsoleOutput implements Output {

    private final VoucherService voucherService;
    private final BlackListUserService blackListUserService;

    public ConsoleOutput(VoucherService voucherService, BlackListUserService blackListUserService) {
        this.voucherService = voucherService;
        this.blackListUserService = blackListUserService;
    }

    @Override
    public void terminate() {
        System.out.println("프로그램을 종료합니다.");
    }

    @Override
    public void showAllVoucher() {
        voucherService.showAll().forEach(System.out::println);
    }

    @Override
    public void showBlackList() {
        blackListUserService.showBlackList().forEach(System.out::println);
    }

    @Override
    public void vouchersBelongToCustomer(UUID customerId) {
        voucherService.findVoucherForCustomer(customerId).forEach(System.out::println);
    }

    @Override
    public void voucherCreated() {
        System.out.println("Voucher Created!");
    }

    @Override
    public void errorMessage() {

    }
}
