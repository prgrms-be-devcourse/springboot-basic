package org.programmers.springboot.basic.util.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external")
public class ExternalProperties {

    private String projDir;
    private String resourceDir;

    public String getProjDir() {
        return projDir;
    }

    public void setProjDir(String projDir) {
        this.projDir = projDir;
    }

    public String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir;
    }
}
