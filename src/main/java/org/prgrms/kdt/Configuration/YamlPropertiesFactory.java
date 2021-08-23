package org.prgrms.kdt.Configuration;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;
import java.util.Properties;

public class YamlPropertiesFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String s, EncodedResource encodedResource) throws IOException {
        var yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(encodedResource.getResource());
        var properties = yamlPropertiesFactoryBean.getObject();
        //프로퍼티 소스를 만들어 줘야함
        return new PropertiesPropertySource(encodedResource.getResource().getFilename(),properties);



    }
}
