package org.prgrms.dev.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

public class YmlPropertiesFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource) throws IOException {
        YamlPropertiesFactoryBean ymlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        ymlPropertiesFactoryBean.setResources(encodedResource.getResource());

        Properties properties = ymlPropertiesFactoryBean.getObject();
        return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
    }

}
