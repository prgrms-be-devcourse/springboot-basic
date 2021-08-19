package org.prgrms.kdtbespring;

import org.prgrms.kdtbespring.config.AppConfiguration;
import org.prgrms.kdtbespring.order.Order;
import org.prgrms.kdtbespring.voucher.Voucher;
import org.prgrms.kdtbespring.voucher.VoucherRepository;
import org.prgrms.kdtbespring.voucher.FixedAmountVoucher;
import org.prgrms.kdtbespring.order.OrderService;
import org.prgrms.kdtbespring.order.OrderItem;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {

        // IoC 컨테이너에 AppConfiguration을 등록
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        UUID customerId = UUID.randomUUID();

        VoucherRepository voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
        VoucherRepository voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");

        System.out.println(MessageFormat.format("voucherRepository {0}", voucherRepository));
        System.out.println(MessageFormat.format("voucherRepository2 {0}", voucherRepository2));
        // Bean Scope이 Singleton 일때는 true가 되고 ProtoType인 경우에는 false가 된다.
        System.out.println(MessageFormat.format("voucherRepository2 == voucherRepository => {0}", voucherRepository2 == voucherRepository));

        // IoC 컨테이너 안에 있는 Bean 중 OrderService 객체를 받아온다.
        OrderService orderService = applicationContext.getBean(OrderService.class);
        //VoucherRepository voucherRepository = annotationConfigApplicationContext.getBean(VoucherRepository.class);

        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        ArrayList<OrderItem> orderItems = new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};

        Order order = orderService.createOrder(customerId, orderItems,voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 100L", order.totalAmount()));

        applicationContext.close();
    }
}
