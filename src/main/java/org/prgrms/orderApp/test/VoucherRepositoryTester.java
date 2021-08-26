package org.prgrms.orderApp.test;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.config.component.AppConfiguration;
import org.prgrms.orderApp.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.orderApp.domain.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.UUID;

public class VoucherRepositoryTester {
    public static void main(String[] args) throws IOException, ParseException {
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
