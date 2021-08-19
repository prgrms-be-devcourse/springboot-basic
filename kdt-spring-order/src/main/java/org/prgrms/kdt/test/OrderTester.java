package org.prgrms.kdt.test;

import org.prgrms.kdt.AppConfiguration;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.OrderItem;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {
        //Spring applicationContext는 객체 생성과 소멸을 관리함 ( 라이프 사이클)
        // 생성
        var applicationContext =  new AnnotationConfigApplicationContext(AppConfiguration.class);

        // 바우처 생성해서 레포지토리에 저장
        // Bean을 가져올때도 어떠한 Bean을 가져올지 선택해야함 , qualifier를 자주이용하지 않음 프라이머리나 Bean을 여러개 등록하지 않음
        var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(),VoucherRepository.class,"memory");

        // voucherRepository가 싱글톤인지 확인해보자.
        var voucherRepository2 = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(),VoucherRepository.class,"memory");
        System.out.println(MessageFormat.format("voucherRepository {0}", voucherRepository));
        System.out.println(MessageFormat.format("voucherRepository2 {0}", voucherRepository2));
        System.out.println(MessageFormat.format("voucherRepository == voucherRepository2 => {0}", voucherRepository2 == voucherRepository));
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
