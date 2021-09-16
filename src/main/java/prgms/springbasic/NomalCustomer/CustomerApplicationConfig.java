package prgms.springbasic.NomalCustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import prgms.springbasic.YamlPropertiesFactory;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@PropertySource(value = "application.properties")
public class CustomerApplicationConfig {
    private final DataSource dataSource;

    @Autowired
    public CustomerApplicationConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
