package com.programmers.voucher.repository;

import com.programmers.voucher.config.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "customer")
@PropertySource(value = "classpath:/customer_query.yml", factory = YamlPropertySourceFactory.class)
public class CustomerQuery {
    private String create;
    private Select select;

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public Select getSelect() {
        return select;
    }

    public void setSelect(Select select) {
        this.select = select;
    }

    public static class Select {
        private String all;
        private String byId;
        private String byVoucher;

        public String getAll() {
            return all;
        }

        public void setAll(String all) {
            this.all = all;
        }

        public String getById() {
            return byId;
        }

        public void setById(String byId) {
            this.byId = byId;
        }

        public String getByVoucher() {
            return byVoucher;
        }

        public void setByVoucher(String byVoucher) {
            this.byVoucher = byVoucher;
        }
    }

}
