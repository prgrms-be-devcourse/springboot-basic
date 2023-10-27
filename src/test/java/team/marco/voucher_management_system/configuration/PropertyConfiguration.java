package team.marco.voucher_management_system.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import team.marco.voucher_management_system.properties.FilePathProperties;

@Configuration
@EnableConfigurationProperties(FilePathProperties.class)
public class PropertyConfiguration {
    @Autowired
    private FilePathProperties filePathProperties;
}
