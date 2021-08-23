package org.prgrms.kdt.test;

import org.prgrms.kdt.configuration.AppConfiguration;
import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderTester {
    public static void main(String[] args) throws IOException {
        //Spring applicationContext는 객체 생성과 소멸을 관리함 ( 라이프 사이클)

        //프로파일로 환경설정가져오기
//        var applicationContext = new AnnotationConfigApplicationContext();
//        applicationContext.register(AppConfiguration.class);
//        var environment = applicationContext.getEnvironment();
//        environment.setActiveProfiles("dev");
//        applicationContext.refresh(); // 적용


        // 생성
        var applicationContext =  new AnnotationConfigApplicationContext(AppConfiguration.class);

        //리소스
        Resource resource = applicationContext.getResource("application.yaml");//프로젝트 실행시 working디렉토리 기준으로 가져옴
        Resource resource3 = applicationContext.getResource("classpath:application.yaml");//프로젝트 실행시 working디렉토리 기준으로 가져옴
        Resource resource2 = applicationContext.getResource("file:sample.txt");//프로젝트 실행시 working디렉토리 기준으로 가져옴
        Resource resource4 = applicationContext.getResource("https://stackoverflow.com/");//

        System.out.println(MessageFormat.format("Resource -> {0}", resource4.getClass().getCanonicalName()));
        //var file = resource4.getFile();//예외 처리 해야함


        //List<String> strings = Files.readAllLines(file.toPath()); //라인마다 리스트에 넣어
        //System.out.println(strings.stream().reduce("",(a,b)->a+"\n"+b));


        ReadableByteChannel readableByteChannel = Channels.newChannel(resource4.getURL().openStream());
        BufferedReader bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
        Stream<String> lines = bufferedReader.lines();
        String collect = lines.collect(Collectors.joining("\n"));
        System.out.println(collect);


//        var orderProperties = applicationContext.getBean(OrderProperties.class);
//        System.out.println(MessageFormat.format("version -> {0}", orderProperties.getVersion()));
//        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", orderProperties.getMinimumOrderAmount()));
//        System.out.println(MessageFormat.format("supportVendors -> {0}", orderProperties.getSupportVendors()));
//        System.out.println(MessageFormat.format("description -> {0}", orderProperties.getDescription()));



        //Property 가져오는것, 형변환도 알아서 할 수 있음
//        ConfigurableEnvironment environment = applicationContext.getEnvironment();
//        String version = environment.getProperty("kdt.version");
//        Integer minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount",Integer.class);
//        List supportVendors = environment.getProperty("kdt.support-vendors", List.class);
//        var description = environment.getProperty("kdt.description",List.class);
//        System.out.println(MessageFormat.format("version -> {0}", version));
//        System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", minimumOrderAmount));
//        System.out.println(MessageFormat.format("supportVendors -> {0}", supportVendors));
//        System.out.println(MessageFormat.format("description -> {0}", description));

        //

        // 바우처 생성해서 레포지토리에 저장
        // Bean을 가져올때도 어떠한 Bean을 가져올지 선택해야함 , qualifier를 자주이용하지 않음 프라이머리나 Bean을 여러개 등록하지 않음
        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(),VoucherRepository.class,"memory");
//
//        // voucherRepository가 싱글톤인지 확인해보자.
//        var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(),VoucherRepository.class,"memory");
//        System.out.println(MessageFormat.format("voucherRepository {0}", voucherRepository));
//        System.out.println(MessageFormat.format("voucherRepository2 {0}", voucherRepository2));
//        System.out.println(MessageFormat.format("voucherRepository == voucherRepository2 => {0}", voucherRepository2 == voucherRepository));

        //프로파일, 퀄리피어
        //여기 뭔가 잘못됨 ㅜㅜ
        //var voucherRepository = applicationContext.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));


        // Order
        var customerId = UUID.randomUUID();
        //var orderContext = new AppConfiguration();
        var orderService = applicationContext.getBean(OrderService.class);
        //var orderService = orderContext.orderService();
        var order = orderService.createOrder(customerId,new ArrayList<OrderItem>(){{
            add(new OrderItem(UUID.randomUUID(),100L,1));
        }},voucher.getVoucherId());


//        var orderItems = new ArrayList<OrderItem>() {{
//            add(new OrderItem(UUID.randomUUID(),100L,1));
//        }};
        // 단순히 100원에서 10원을 뺄거고 이 10원은 fixedAmount를 통해 하드코딩 되어있다.
        // 의존 관계가 order -> FixedAmountVoucher 로 FAV를 수정하면 order의 로직이 변하게 된다.
        // 강한 결합도
        //var order = new Order(UUID.randomUUID(),customerId,orderItems,10L);

        //일반적인 제어의 구조 : order에서 모든 기능을 선택해서 사용
        //var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(),10L);
        //var order = new Order(UUID.randomUUID(),customerId,orderItems,fixedAmountVoucher);

        //제어의 역전, 라이브러리와 다르게 프레임워크에서
        //우리는 기능을 만들고 프레임워크가 이것을 실행하게 만든다.

        //확인
        Assert.isTrue(order.totalAmount()==90L, MessageFormat.format("totalAmount{0} is not 90L", order.totalAmount()));

        //소멸
        applicationContext.close();
    }

}
