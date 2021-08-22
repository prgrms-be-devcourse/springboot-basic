package org.prgrms.kdt;


import org.prgrms.kdt.Order.Order;
import org.prgrms.kdt.Voucher.Voucher;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//설정 메타 데이터(빈을 정의 한 도면인것을)인것을 알려줘야한다
@Configuration
//자동적으로 빈을 찾아주는 어노테이션
@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
//의존 관계를 맺는 클래스
public class AppConfiguration {


}