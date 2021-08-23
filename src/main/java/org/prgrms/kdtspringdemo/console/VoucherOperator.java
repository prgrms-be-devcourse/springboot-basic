package org.prgrms.kdtspringdemo.console;

import org.prgrms.kdtspringdemo.VoucherType;
import org.prgrms.kdtspringdemo.configuration.AppConfiguration;
import org.prgrms.kdtspringdemo.voucher.Voucher;
import org.prgrms.kdtspringdemo.voucher.VoucherService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.text.MessageFormat;
import java.util.Optional;

public class VoucherOperator implements CommandOperator {
    private final VoucherService voucherService;

    public VoucherOperator() {
        var application = new AnnotationConfigApplicationContext(AppConfiguration.class);
        voucherService = application.getBean(VoucherService.class);
    }

    @Override
    public void create(String[] splitList) {
        Optional<Voucher> voucher = createVoucher(splitList);
        voucher.ifPresent(voucherService::saveVoucher);
    }

    public Optional<Voucher> createVoucher(String[] splitList) {
        if (splitList.length != 2) return Optional.empty();

        Optional<Voucher> voucher = Optional.empty();
        if (VoucherType.isInVoucherType(splitList[0])) {
            voucher = VoucherType.getVoucher(splitList[0], Long.parseLong(splitList[1]));

            System.out.println("This is create : " + voucher);
        } else {
            System.out.println("[ERROR]Invalid create command");
            System.out.println(MessageFormat.format("Your input : {0}, {1}", splitList[0], splitList[1]));
        }
        return voucher;
    }

    @Override
    public void printAll() {
        voucherService.printAllVoucher();
    }
}
