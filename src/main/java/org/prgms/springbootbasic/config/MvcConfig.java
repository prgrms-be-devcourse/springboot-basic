package org.prgms.springbootbasic.config;

import ch.qos.logback.classic.pattern.MessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/static/img/");

        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/");

        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
//        MarshallingHttpMessageConverter messageConverter = new MarshallingHttpMessageConverter();
//        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
//        messageConverter.setMarshaller(xStreamMarshaller);
//        messageConverter.setUnmarshaller(xStreamMarshaller);
//        converters.add(0, messageConverter);
//    }
}
