package org.prgms.springbootbasic;

import org.prgms.springbootbasic.management.repository.MemoryVoucherRepository;
import org.prgms.springbootbasic.management.repository.VoucherRepository;
import org.prgms.springbootbasic.management.service.ConsoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {
    @Bean
    public VoucherRepository memoryVoucherRepository() {
        return new MemoryVoucherRepository();
    }

    @Bean
    public ConsoleService consoleService() {
        return new ConsoleService();
    }
}
