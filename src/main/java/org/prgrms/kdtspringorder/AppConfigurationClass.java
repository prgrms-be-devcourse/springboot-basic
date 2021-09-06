package org.prgrms.kdtspringorder;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {
                "org.prgrms.kdtspringorder.io",
                "org.prgrms.kdtspringorder.voucher",
                "org.prgrms.kdtspringorder.customer"
        },
        basePackageClasses = {App.class}
)
public class AppConfigurationClass {

}
