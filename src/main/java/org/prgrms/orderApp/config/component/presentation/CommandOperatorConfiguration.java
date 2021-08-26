package org.prgrms.orderApp.config.component.presentation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgrms.orderApp.presentation.commandOperator"})
public class CommandOperatorConfiguration {
}
