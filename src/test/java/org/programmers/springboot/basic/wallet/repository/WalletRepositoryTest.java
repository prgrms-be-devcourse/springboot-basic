package org.programmers.springboot.basic.wallet.repository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.programmers.springboot.basic.aop.LoggerAspect;
import org.programmers.springboot.basic.config.DataSourceConfig;
import org.programmers.springboot.basic.config.DataSourceProperties;
import org.programmers.springboot.basic.config.VoucherConfig;
import org.programmers.springboot.basic.domain.wallet.mapper.WalletEntityMapperImpl;
import org.programmers.springboot.basic.domain.wallet.mapper.WalletMapper;
import org.programmers.springboot.basic.domain.wallet.repository.JdbcWalletRepository;
import org.programmers.springboot.basic.util.generator.UUIDRandomGenerator;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ComponentScan(
        basePackages = {"org.programmers.springboot.basic"},
        basePackageClasses = JdbcWalletRepository.class
)
@ContextConfiguration(classes = {
        LoggerAspect.class,
        DataSourceConfig.class,
        DataSourceProperties.class,
        VoucherConfig.class,
        UUIDRandomGenerator.class,
        WalletEntityMapperImpl.class,
        JdbcWalletRepository.class,
        WalletMapper.class
})
@EnableConfigurationProperties(value = DataSourceProperties.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-test.yaml"})
@SpringBootTest
public class WalletRepositoryTest {


}
