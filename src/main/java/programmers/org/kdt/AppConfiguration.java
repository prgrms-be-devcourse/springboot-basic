package programmers.org.kdt;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import programmers.org.kdt.configuration.YamlPropertiesFactory;

@Configuration
@ComponentScan(basePackages = {"programmers.org.kdt.engine", "programmers.org.kdt.configuration"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@PropertySource(value = "application.yaml", factory = YamlPropertiesFactory.class)
@EnableConfigurationProperties
public class AppConfiguration {
}

