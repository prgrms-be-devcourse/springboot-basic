package com.example.commandlineapplication.config;

import com.example.commandlineapplication.VoucherController;
import com.example.commandlineapplication.io.Input;
import com.example.commandlineapplication.io.Output;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean
  public Input input() {
    return new Input();
  }

  @Bean
  public Output output() {
    return new Output();
  }

  @Bean
  public VoucherController voucherController() {
    return new VoucherController(input(), output());
  }
}
