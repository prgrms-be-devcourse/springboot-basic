package org.prgrms.kdt.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("version.properties")
public class VersionProvider {
    private final String version;

    public VersionProvider(@Value("${version:v0.0.0}") final String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

}