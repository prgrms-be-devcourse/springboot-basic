package org.prgrms.kdt.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("version.properties")
public class VersionProvider { //VersionProvider를 주입받아서 보내줄수 있음.
    //ex) OderSerivce에서 사용함
    private final String version;

    public VersionProvider(@Value("${version:v0.0.0}")String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

}
