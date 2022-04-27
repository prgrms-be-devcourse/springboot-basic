package com.blessing333.springbasic.config;

import com.blessing333.springbasic.voucher.api.VoucherInformation;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Profile("!console")
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        var messageConverter = new MarshallingHttpMessageConverter();
        var xStreamMarshaller = configureXStreamMarshaller();
        messageConverter.setMarshaller(xStreamMarshaller);
        messageConverter.setUnmarshaller(xStreamMarshaller);
        converters.add(messageConverter);
    }

    private XStreamMarshaller configureXStreamMarshaller(){
        var xStreamMarshaller = new XStreamMarshaller();
        Map<String,Class<?>> alias = new HashMap<>();
        alias.put("Voucher", VoucherInformation.class);
        xStreamMarshaller.setAliases(alias);
        return xStreamMarshaller;
    }
}
