package prgms.vouchermanagementapp.blacklist;

import org.springframework.stereotype.Component;

@Component
public class BlacklistManager {

    private static final String BLACKLIST_FILEPATH = "classpath:customer_blacklist.csv";

    public String getBlacklistFilepath() {
        return BLACKLIST_FILEPATH;
    }
}
