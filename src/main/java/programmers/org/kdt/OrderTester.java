package programmers.org.kdt;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import programmers.org.kdt.engine.order.OrderProperties;
import programmers.org.kdt.engine.order.OrderService;
import programmers.org.kdt.engine.voucher.FixedAmountVoucher;
import programmers.org.kdt.engine.voucher.VoucherRepository;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) throws IOException {
        //open application context
        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        //environment properties
//        var environment = applicationContext.getEnvironment();
//        var version = environment.getProperty("kdt.version");
//        var minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
//        var supportVendors = environment.getProperty("kdt.support-vendors", List.class);
//        var description = environment.getProperty("kdt.description", List.class);
//        System.out.println(MessageFormat.format("version -> {0}", version));
//        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", minimumOrderAmount));
//        System.out.println(MessageFormat.format("supportVendors -> {0}", supportVendors));
//        System.out.println(MessageFormat.format("description -> {0}", description));
        var orderProperties = applicationContext.getBean(OrderProperties.class);
//        System.out.println(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
//        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
//        System.out.println(MessageFormat.format("supportVendors -> {0}", orderProperties.getSupportVendors()));
//        System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));

        var resource = applicationContext.getResource("application.yaml");
        var resource2 = applicationContext.getResource("classpath:application.yaml");
        var resource3 = applicationContext.getResource("file:sample.txt");
        var resource4 = applicationContext.getResource("https://stackoverflow.com");
        //System.out.println(MessageFormat.format("Resource ->{0}", resource4.getClass().getCanonicalName()));
        //var strings= Files.readAllLines(resource.getFile().toPath());
        //System.out.println(strings.stream().reduce("", (a, b)->a+"\n"+b));
        var readableByteChannel = Channels.newChannel(resource4.getURL().openStream());
        var bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
        var lines = bufferedReader.lines();
        var contents= lines.collect(Collectors.joining("\n"));
        System.out.println(contents);

        //random customerId
        var customerId = UUID.randomUUID();

        //voucher
        var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        //order
        var orderService = applicationContext.getBean(OrderService.class);
        /*var order = orderService.createOrder(customerId, new ArrayList<OrderItem>(){{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }} , voucher.getVoucherId()
        );

         */

        //check
        //Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

        //close application context
        applicationContext.close();
    }
}
