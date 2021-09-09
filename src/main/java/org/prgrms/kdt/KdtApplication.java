package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.repository.JdbcVoucherRepository;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(
        basePackages = {"org.prgrms.kdt.command", "org.prgrms.kdt.configuration", "org.prgrms.kdt.order", "org.prgrms.kdt.voucher"}
)
public class KdtApplication {
    public static void main(final String[] args) {
        final var springApplication = new SpringApplication(KdtApplication.class);
//        springApplication.setAdditionalProfiles("local");
        final var applicationContext = springApplication.run(args);
//        var applicationContext = SpringApplication.run(KdtApplication.class, args);

        final var orderProperties = applicationContext.getBean(OrderProperties.class);
        System.out.println(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
        System.out.println(MessageFormat.format("supportVendors -> {0}", orderProperties.getSupportVendors()));
        System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

        final var customerId = UUID.randomUUID();
        final var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        final var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository instanceof JdbcVoucherRepository));
        System.out.println(MessageFormat.format("is Jdbc Repo -> {0}", voucherRepository.getClass().getCanonicalName()));

    }

}