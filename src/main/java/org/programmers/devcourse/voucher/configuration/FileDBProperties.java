package org.programmers.devcourse.voucher.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ConfigurationProperties("db")
@Profile("dev")
@PropertySource(value = "/application-dev.yaml", factory = YamlPropertySourceFactory.class)
public class FileDBProperties {

  private String filename;

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }
}
