package com.programmers.vouchermanagement.properties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "file")
@EnableConfigurationProperties
public class AppProperties {
    private Resources resources;
    private Map<String, String> domains = new HashMap<>();

    public Resources getResources() {
        return resources;
    }

    public Map<String, String> getDomains() {
        return domains;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public void setDomains(Map<String, String> domains) {
        this.domains = domains;
    }
}
