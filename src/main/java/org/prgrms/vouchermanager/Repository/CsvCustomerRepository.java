package org.prgrms.vouchermanager.Repository;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@ConfigurationProperties(prefix = "kdt")
public class CsvCustomerRepository implements CustomerRepositroy{

    @Override
    public List<Map<String, String>> findAll() {
        return null;
    }
}
