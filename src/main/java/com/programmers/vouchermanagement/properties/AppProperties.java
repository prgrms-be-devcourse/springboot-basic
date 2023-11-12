package com.programmers.vouchermanagement.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties(prefix = "file")
public record AppProperties(Resources resources, Map<String, Domain> domains) {
}
