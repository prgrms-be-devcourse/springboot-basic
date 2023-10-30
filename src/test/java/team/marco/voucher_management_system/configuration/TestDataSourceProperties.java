package team.marco.voucher_management_system.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("spring.datasource")
public record TestDataSourceProperties(
        String driver_class_name,
        String url,
        String userName,
        String password) {
}
