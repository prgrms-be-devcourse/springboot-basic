package com.programmers.vouchermanagement.configuration.properties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties(prefix = "file")
public class AppProperties {
    private final Resources resources;
    private final Map<String, String> domains;

    @ConstructorBinding
    public AppProperties(Resources resources, Map<String, String> domains) {
        this.resources = resources;
        this.domains = domains;
    }

    public Resources getResources() {
        return resources;
    }

    public Map<String, String> getDomains() {
        return domains;
    }
}
