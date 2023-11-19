package org.prgms.springbootbasic.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedOrigins("*");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MarshallingHttpMessageConverter messageConverter = new MarshallingHttpMessageConverter();
        XStreamMarshaller marshaller = new XStreamMarshaller();
        messageConverter.setMarshaller(marshaller);
        messageConverter.setUnmarshaller(marshaller);

        converters.add(messageConverter);
    }
}
