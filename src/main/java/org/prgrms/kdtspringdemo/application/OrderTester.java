package org.prgrms.kdtspringdemo.application;

import org.prgrms.kdtspringdemo.configuration.AppConfiguration;
import org.prgrms.kdtspringdemo.order.OrderItem;
import org.prgrms.kdtspringdemo.order.OrderService;
import org.prgrms.kdtspringdemo.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.JdbcVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
//    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);
//
//    public static void main(String[] args) throws IOException {
//        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
//        var application = new AnnotationConfigApplicationContext();
//        application.register(AppConfiguration.class);
//        application.refresh();
//
//
//        var customerId = UUID.randomUUID();
//
//        var voucherRepository = application.getBean(VoucherRepository.class);
//        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));
//
//        logger.warn("logger name -> {}", logger.getName());
//        logger.error(MessageFormat.format("is Jdbc Repo ->{0}", voucherRepository instanceof JdbcVoucherRepository));
//        logger.warn(MessageFormat.format("is Jdbc Repo ->{0}", voucherRepository.getClass().getCanonicalName()));
//
//
//        var orderService = application.getBean(OrderService.class);
//        var order = orderService.createOrder(customerId, new ArrayList<>() {{
//            add(new OrderItem(UUID.randomUUID(), 100L, 1));
//        }}, voucher.getVoucherId());
//
//        Assert.isTrue(order.totalAmount() == 90L,  MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
//    }
}
