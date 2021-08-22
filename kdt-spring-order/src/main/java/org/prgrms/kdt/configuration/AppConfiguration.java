package org.prgrms.kdt.configuration;

import org.prgrms.kdt.controller.VoucherController;
import org.prgrms.kdt.io.IoConsole;
import org.prgrms.kdt.repository.*;
import org.prgrms.kdt.service.OrderService;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;

//component Scan으로 바꾸기 -> 나머지 @ 등록 , ,"org.prgrms.kdt.io"

//@ComponentScan( basePackages = { "org.prgrms.kdt.controller"},
//        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = VoucherController.class)
//)
@Configuration
@ComponentScan(basePackages = {"org.prgrms.kdt.domain",
        "org.prgrms.kdt.repository",
        "org.prgrms.kdt.service",
        "org.prgrms.kdt.factory",
        "org.prgrms.kdt.fileutil",
        "org.prgrms.kdt.controller"
      },
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = VoucherController.class)
)
@PropertySource(value="application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {




}
