package prgms.springbasic;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import prgms.springbasic.voucher.JdbcVoucherRepository;
import prgms.springbasic.voucher.VoucherRepository;

import javax.sql.DataSource;

@Configuration
@ComponentScan
public class CommandLineAppConfig {
    private final DataSource dataSource;

    @Autowired
    public CommandLineAppConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public VoucherRepository voucherRepository(){
        return new JdbcVoucherRepository(dataSource);
    }
}
