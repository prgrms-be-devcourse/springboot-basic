package org.programmers.devcourse.voucher.configuration;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.lang.NonNull;

public class YamlPropertySourceFactory implements PropertySourceFactory {

  @Override
  public PropertySource<?> createPropertySource(String name, @NonNull EncodedResource resource) throws IOException {
    var factory = new YamlPropertiesFactoryBean();
    factory.setResources(resource.getResource());
    Properties properties = factory.getObject();

    return new PropertiesPropertySource(
        Objects.requireNonNull(resource.getResource().getFilename()),
        Objects.requireNonNull(properties));
  }
}
