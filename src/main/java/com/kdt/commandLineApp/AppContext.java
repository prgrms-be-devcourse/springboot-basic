package com.kdt.commandLineApp;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages ="com.kdt.commandLineApp")
@PropertySource("application.yaml")
public class AppContext {
}
