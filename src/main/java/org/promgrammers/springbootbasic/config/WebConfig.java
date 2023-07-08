package org.promgrammers.springbootbasic.config;

import org.promgrammers.springbootbasic.domain.voucher.util.VoucherTypeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new VoucherTypeConverter());
    }
}
