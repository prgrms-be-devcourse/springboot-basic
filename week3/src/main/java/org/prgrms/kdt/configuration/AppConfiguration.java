package org.prgrms.kdt.configuration;

import org.springframework.boot.ansi.AnsiOutput;

public class AppConfiguration {
    void config(){
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    }
}
