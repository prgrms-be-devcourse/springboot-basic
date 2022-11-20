package prgms.vouchermanagementapp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "files")
@ConstructorBinding
public class FileConfig {

    private final String customerBlacklist;
    private final String voucherRecord;

    public FileConfig(String customerBlacklist, String voucherRecord) {
        this.customerBlacklist = customerBlacklist;
        this.voucherRecord = voucherRecord;
    }

    public String getCustomerBlacklist() {
        return this.customerBlacklist;
    }

    public String getVoucherRecord() {
        return this.voucherRecord;
    }
}
