package org.programmers.voucher;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@EnableWebMvc
@Configuration
public class VoucherWebApplicationInitializer implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        var messageConverter = new MarshallingHttpMessageConverter();
        var xStreamMarshaller = new XStreamMarshaller();
        messageConverter.setMarshaller(xStreamMarshaller);
        messageConverter.setUnmarshaller(xStreamMarshaller);
        converters.add(0,messageConverter);

        var javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        var modules = Jackson2ObjectMapperBuilder.json().modules(javaTimeModule);
        converters.add(1,new MappingJackson2HttpMessageConverter(modules.build()));
    }
}
