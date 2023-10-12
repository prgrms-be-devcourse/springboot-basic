package org.prgrms.kdtspringdemo.voucher;

import org.prgrms.kdtspringdemo.voucher.service.VoucherService;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public enum VoucherFunction {
    CREATE("create", "바우처 등록", VoucherService::createVoucher),
    LIST_ALL_VOUCHERS("list", "바우처 목록", VoucherService::getVoucherList),
    EXIT("exit", "프로그램을 종료합니다.", VoucherService::endVoucherService);
    private final String fun;
    private final String description;
    private final Consumer<VoucherService> voucherServiceConsumer;

    VoucherFunction(String fun, String description, Consumer<VoucherService> voucherServiceConsumer) {
        this.fun = fun;
        this.description = description;
        this.voucherServiceConsumer = voucherServiceConsumer;
    }

    public static VoucherFunction findByCode(String fun) {
        return Arrays.stream(values())
                .filter(option -> option.fun.equals(fun))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("해당 명령어가 존재하지 않습니다."));
    }

    public void execute(VoucherService voucherService) {
        this.voucherServiceConsumer.accept(voucherService);
    }
}
