package org.prgrms.assignment.voucher.configuration;

import org.prgrms.assignment.voucher.controller.VoucherController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@ComponentScan(
        basePackages = {"org.prgrms.assignment.voucher"}
)
public class VoucherMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("*");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MarshallingHttpMessageConverter messageConverter = new MarshallingHttpMessageConverter();
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        messageConverter.setMarshaller(xStreamMarshaller);
        messageConverter.setUnmarshaller(xStreamMarshaller);
        converters.add(0, messageConverter);
    }

}
