package org.prgms;

import org.prgms.order.OrderItem;
import org.prgms.order.OrderService;
import org.prgms.voucher.FixedAmountVoucher;
import org.prgms.voucher.MemoryVoucherRepository;
import org.prgms.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    private static final Logger log = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) throws IOException {

        // 프로파일 적용 후  refresh를 해야함. 안그러면 이미 디폴트 프로파일로 읽어져서 원하는 결과를 얻을 수 없음
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("dev");;
        applicationContext.register(AppConfiguration.class);
        applicationContext.refresh();
        
//        var version = environment.getProperty("kdt.version");
//        var minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
//
//        log.info("version -> {}", version);
//        log.info("minimumOrderAmount -> {}", minimumOrderAmount);

        var customerId = UUID.randomUUID();
        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.save(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        System.out.println(MessageFormat.format("is MemoryRepo -> {0}", voucherRepository instanceof MemoryVoucherRepository));
        System.out.println(MessageFormat.format("is MemoryRepo -> {0}", voucherRepository.getClass().getCanonicalName()));


        var orderService = applicationContext.getBean(OrderService.class);
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());


        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount({0}) is not 90L", order.totalAmount()));
    }
}
