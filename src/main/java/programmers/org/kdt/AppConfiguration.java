package programmers.org.kdt;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
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
/*
    @Bean
    public VoucherRepository voucherRepository() {
        return new VoucherRepository() {
            @Override
            public Optional<Voucher> findById(UUID voucherId) {
                return Optional.empty();
            }
        };
    }

    @Bean
    public OrderRepository orderRepository() {
        return new OrderRepository() {
            @Override
            public Order insert(Order order) {

            }
        };
    }*/
    /*
    @Bean
    public VoucherService voucherService(VoucherRepository voucherRepository) {
        return new VoucherService(voucherRepository);
    } //스프링이 알아서 만들때 전달해준다.

     */
    /*
    @Bean
    public VoucherService voucherService() {
        return new VoucherService(voucherRepository());
    }
     */
    /*
    @Bean
    public OrderService orderService(VoucherService voucherService, OrderRepository orderRepository) {
        return new OrderService(voucherService, orderRepository);
    }

     */
    /*
    @Bean
    public  OrderService orderService() {
        return new OrderService(voucherService(), orderRepository());
    }
     */

  /*
  @Bean(initMethod = "init")
  public BeanOne beanOne() {
    return new BeanOne();
  }*/
}
/*
class BeanOne implements InitializingBean {
  public void init() {
    System.out.println("[BeanOne] init called!");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("[BeanOne] afterPropertiesSet called!");
  }
}
*/
