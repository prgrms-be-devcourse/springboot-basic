package com.voucher.vouchermanagement.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfiguration {

  @Value("${db.blacklist.db_name}")
  private String blackListDbName;
  @Value("${db.voucher.db_name}")
  private String voucherDbName;
  @Value("${db.path}")
  private String dbDirectory;

  @Bean("voucherDb")
  @Profile("prod")
  File voucherDb(ApplicationContext context) throws IOException {
    Path path = Paths.get(System.getProperty("user.dir"), dbDirectory, voucherDbName);

    return context.getResource("file:/" + path).getFile();
  }

  @Bean("blacklistDb")
  File blacklist(ApplicationContext context) throws IOException {
    Path path = Paths.get(System.getProperty("user.dir"), dbDirectory, blackListDbName);

    return context.getResource("file:/" + path).getFile();
  }

}
