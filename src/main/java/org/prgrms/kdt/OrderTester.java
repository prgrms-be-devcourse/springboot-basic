package org.prgrms.kdt;

import org.prgrms.kdt.ValueObject.OrderItem;
import org.prgrms.kdt.entity.FixedAmountVoucher;
import org.prgrms.kdt.entity.Order;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) {
        var customerId = UUID.randomUUID();
        var orderItems = new ArrayList<OrderItem>() {{
            // 100원짜리 한개를 들고있도록함
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }};
        // 여기서 퍼센트바우처를 사용할지 fixed바우처를 사용할지에 따라 객체를 생성하고 voucher를 오더에 넣으면 된다.
        var fixedAmountVoucher = new FixedAmountVoucher(UUID.randomUUID(), 10L);


        // 10원을 디스카운트
        //var order = new Order(UUID.randomUUID(), customerId, orderItems, 20L);

        // 마지막 인자를 인터페이스 객체로 넣어줌
        // 런타임 디펜던시가 됨!!! (이전에는 컴파일 디펜던시)
        // 이후에 퍼센트바우처로 바꿔도 전혀 문제없다. -> 의존성이 약한 코드가 됨
        // 클래스간의 의존성은 컴파일타임 디펜던시고 객체간의 의존성은 런타임 디펜던시가된다.
        var order = new Order(UUID.randomUUID(), customerId, orderItems, fixedAmountVoucher);

        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

    }
}
