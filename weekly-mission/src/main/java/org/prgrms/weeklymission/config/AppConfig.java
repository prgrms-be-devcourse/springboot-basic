package org.prgrms.weeklymission.config;

import org.prgrms.weeklymission.WeeklyMissionApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = WeeklyMissionApplication.class)
public class AppConfig {
}
