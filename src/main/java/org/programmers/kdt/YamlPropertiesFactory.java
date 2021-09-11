package org.programmers.kdt;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;

import java.io.IOException;

public class YamlPropertiesFactory implements PropertySourceFactory {

	@Override
	public PropertySource<?> createPropertySource(String str, EncodedResource encodedResource) throws IOException {
		YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
		yamlPropertiesFactoryBean.setResources(encodedResource.getResource());

		var properties = yamlPropertiesFactoryBean.getObject();
		return new PropertiesPropertySource(encodedResource.getResource().getFilename(), properties);
	}
}
