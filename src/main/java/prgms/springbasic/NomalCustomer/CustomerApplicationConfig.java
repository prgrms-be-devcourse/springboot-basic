package prgms.springbasic.NomalCustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class CustomerApplicationConfig {
    private final DataSource dataSource;

    @Autowired
    public CustomerApplicationConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
