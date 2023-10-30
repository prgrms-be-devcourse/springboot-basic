package team.marco.voucher_management_system.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.PropertySource;
import team.marco.voucher_management_system.factory.YamlPropertiesFactory;
import team.marco.voucher_management_system.properties.FilePathProperties;

@TestConfiguration
@EnableConfigurationProperties({FilePathProperties.class, TestDataSourceProperties.class})
@PropertySource(value = "classpath:application.yml", factory = YamlPropertiesFactory.class)
public class TestPropertyConfiguration {
}
