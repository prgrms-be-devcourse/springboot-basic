package com.programmers.vouchermanagement.configuration;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

@Deprecated
public class YamlPropertiesFactory implements PropertySourceFactory {
    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) {
        var factoryBean = new YamlPropertiesFactoryBean();
        factoryBean.setResources(resource.getResource());

        var properties = factoryBean.getObject();
        return new PropertiesPropertySource(resource.getResource().getFilename(), properties);
    }
}
