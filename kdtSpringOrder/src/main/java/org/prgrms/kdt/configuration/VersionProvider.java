package org.prgrms.kdt.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

//필요한곳에 주입해주면 된다.
//만약 orderService에서 필요하면 orderService에서 생성자로 주입받아서 가져다 쓰면된다.
@Component
@PropertySource("version.properties")
public class VersionProvider {

    private final String version;

    public VersionProvider(@Value("${version:v0.0.0}") String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
