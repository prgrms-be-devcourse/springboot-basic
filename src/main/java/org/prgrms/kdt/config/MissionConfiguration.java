package org.prgrms.kdt.config;

import org.prgrms.kdt.order.Order;
import org.prgrms.kdt.voucher.Voucher;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by yhh1056
 * Date: 2021/08/18 Time: 1:30 오전
 */
@ComponentScan(basePackageClasses = {Order.class, Voucher.class})
public class MissionConfiguration {

}
