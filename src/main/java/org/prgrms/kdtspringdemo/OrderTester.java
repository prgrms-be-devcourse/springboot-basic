package org.prgrms.kdtspringdemo;

import org.prgrms.kdtspringdemo.order.OrderItem;
import org.prgrms.kdtspringdemo.order.OrderService;
import org.prgrms.kdtspringdemo.voucher.FixedAmountVoucher;
import org.prgrms.kdtspringdemo.voucher.JdbcVoucherRepository;
import org.prgrms.kdtspringdemo.voucher.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) throws IOException {
        var application = new AnnotationConfigApplicationContext();
        application.register(AppConfiguration.class);
        var environment = application.getEnvironment();
        environment.setActiveProfiles("local");
        application.refresh();

//        var resource = application.getResource("classpath:application.yaml");
//        var resource = application.getResource("file:test/sample.txt");
//        System.out.println(MessageFormat.format("Resource >> {0}", resource.getClass().getCanonicalName()));
//        List<String> strings = Files.readAllLines(resource.getFile().toPath());
//        System.out.println(strings.stream().reduce("", (a,b) -> a + "\n" + b));

//        var resource = application.getResource("https://stackoverflow.com/");
//        var readableByteChannel = Channels.newChannel(resource.getURL().openStream());
//        var bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
//        var contents = bufferedReader.lines().collect(Collectors.joining("\n"));
//        System.out.println(contents);

        var customerId = UUID.randomUUID();

        var voucherRepository = application.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        System.out.println(MessageFormat.format("is Jdbc Repo ->{0}", voucherRepository instanceof JdbcVoucherRepository));
        System.out.println(MessageFormat.format("is Jdbc Repo ->{0}", voucherRepository.getClass().getCanonicalName()));


        var orderService = application.getBean(OrderService.class);
        var order = orderService.createOrder(customerId, new ArrayList<>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,  MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));
    }
}
