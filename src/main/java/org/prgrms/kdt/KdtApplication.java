package org.prgrms.kdt;

import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.engine.order.OrderItem;
import org.prgrms.kdt.engine.order.OrderProperties;
import org.prgrms.kdt.engine.order.OrderService;
import org.prgrms.kdt.engine.voucher.FixedAmountVoucher;
import org.prgrms.kdt.engine.voucher.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

@SpringBootApplication
@ComponentScan(
        basePackages = {"org.prgrms.kdt.engine", "org.prgrms.kdt.configuration"}
)
public class KdtApplication {
    public static void main(String[] args) {
        var springApplication = new SpringApplication(KdtApplication.class);
        springApplication.setAdditionalProfiles("production");
        var applicationContext = springApplication.run(args);

        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
        System.out.println(MessageFormat.format("repository -> {0}", voucherRepository.getClass()));;

        var orderProperties = applicationContext.getBean(OrderProperties.class);
        System.out.println(orderProperties.getVersion());
        System.out.println(orderProperties.getSupportVendors());

        var orderService = applicationContext.getBean(OrderService.class);
        var customerId = UUID.randomUUID();
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        applicationContext.close();
    }
}
