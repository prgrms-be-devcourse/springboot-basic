package org.programmers.devcourse.voucher.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("dev")
@ConfigurationProperties("jdbc")
@PropertySource(value = "/application-jdbc.yaml", factory = YamlPropertySourceFactory.class)
@Getter
@Setter
public class DevJdbcProperties implements JdbcProperties {

  private String user;
  private String password;
  private String url;
}
