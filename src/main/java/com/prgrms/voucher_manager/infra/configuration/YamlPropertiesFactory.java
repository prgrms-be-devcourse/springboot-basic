//package com.prgrms.voucher_manager.configuration;
//
//import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
//import org.springframework.core.env.PropertiesPropertySource;
//import org.springframework.core.env.PropertySource;
//import org.springframework.core.io.support.EncodedResource;
//import org.springframework.core.io.support.PropertySourceFactory;
//
//import java.io.IOException;
//import java.util.Properties;
//
//public class YamlPropertiesFactory implements PropertySourceFactory {
//
//    @Override
//    public PropertySource<?> createPropertySource(String name, EncodedResource encodeResource) throws IOException {
//        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
//        yamlPropertiesFactoryBean.setResources(encodeResource.getResource());
//
//        Properties properties = yamlPropertiesFactoryBean.getObject();
//        return new PropertiesPropertySource(encodeResource.getResource().getFilename(),properties);
//    }
//}
