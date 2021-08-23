package org.prgrms.kdt.configuration;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Objects;

public class YamlPropertiesFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource) throws IOException {
        var yamlPropertiesFactoryBean =  new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(encodedResource.getResource());

        var properties = yamlPropertiesFactoryBean.getObject();
        return new PropertiesPropertySource(Objects.requireNonNull(encodedResource.getResource().getFilename()), properties);
    }

}
