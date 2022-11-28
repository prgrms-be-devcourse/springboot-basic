package org.prgrms.springorder.config;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
            .allowedOrigins("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
            .allowedOrigins("*");
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MarshallingHttpMessageConverter xmlMessageConverter = new MarshallingHttpMessageConverter();

        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setAutodetectAnnotations(true);

        xmlMessageConverter.setMarshaller(xStreamMarshaller);
        xmlMessageConverter.setUnmarshaller(xStreamMarshaller);

        converters.add( xmlMessageConverter);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        Jackson2ObjectMapperBuilder modules = Jackson2ObjectMapperBuilder.json()
            .modules(javaTimeModule);

        converters.add(new MappingJackson2HttpMessageConverter(modules.build()));
    }

}
