package org.prgrms.kdt;

import org.prgrms.kdt.controller.InputController;
import org.prgrms.kdt.controller.OutputController;
import org.prgrms.kdt.helper.MessageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

//VoucherService, OrderService, OrderRepository, VoucherRepository 의 생성에 대한 책임을 가지게 된다.
//IoC 컨테이너 역할을 하기때문에 객체들의 의존관계 설정, 객체 생성, 파괴를 담당한다.
@Configuration
@ComponentScan
public class AppConfiguration {

}