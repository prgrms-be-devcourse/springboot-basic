package org.prgrms.kdt.configuration;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

public class YamlPropertiesFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(final String s, final EncodedResource encodedResource) {
        final var yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(encodedResource.getResource());

        final var properties = yamlPropertiesFactoryBean.getObject();
        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }
}