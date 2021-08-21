package org.prgrms.orderApp.test;

import org.prgrms.orderApp.config.AppConfiguration;
import org.prgrms.orderApp.model.voucher.FixedAmountVoucher;
import org.prgrms.orderApp.repository.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.UUID;

public class VoucherRepositoryTester {
    public static void main(String[] args) {
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        var voucherId_1 = UUID.randomUUID();
        var voucherId_2 = UUID.randomUUID();

        var vourcherRepository = applicationContext.getBean(VoucherRepository.class);
        vourcherRepository.save(new FixedAmountVoucher(voucherId_1,90));
        vourcherRepository.save(new FixedAmountVoucher(voucherId_2,10));
        vourcherRepository.toString();
        vourcherRepository.findAll();

    }
}
