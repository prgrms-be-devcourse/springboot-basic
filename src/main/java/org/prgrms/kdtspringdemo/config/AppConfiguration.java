package org.prgrms.kdtspringdemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;

//https://github.com/eugenp/tutorials/blob/f440eba4b73280616f9cce892ae3ca07071e404f/spring-boot-rest/src/main/java/com/baeldung/spring/ConverterExtensionsConfig.java
//https://www.baeldung.com/spring-httpmessageconverter-rest

@Configuration
public class AppConfiguration {

    //    @Bean
//    public HttpMessageConverter<Objects> createXmlHttpMessageConverter(){
//        MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();
//        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
//        xmlConverter.setMarshaller(xStreamMarshaller);
//        xmlConverter.setUnmarshaller(xStreamMarshaller);
//        return xmlConverter;
//    }
    @Bean
    public HttpMessageConverter<Object> createXmlHttpMessageConverter() {
        final MarshallingHttpMessageConverter xmlConverter = new MarshallingHttpMessageConverter();

        final XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
        xmlConverter.setMarshaller(xstreamMarshaller);
        xmlConverter.setUnmarshaller(xstreamMarshaller);

        return xmlConverter;
    }
}
