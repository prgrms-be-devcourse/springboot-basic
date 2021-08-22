package org.prgrms.kdt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * Created by yhh1056
 * Date: 2021/08/22 Time: 5:03 오후
 */

@Component
@PropertySource("version.properties")
public class VersionProvider {

    private final String version;

    public VersionProvider(@Value("${version}") String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
