package com.programmers.voucher.repository;

import com.programmers.voucher.config.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "voucher")
@PropertySource(value = "voucher_query.yml", factory = YamlPropertySourceFactory.class)
public class VoucherQuery {
    private String create;
    private Select select;
    private Update update;
    private Delete delete;

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

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public Delete getDelete() {
        return delete;
    }

    public void setDelete(Delete delete) {
        this.delete = delete;
    }

    public static class Select {
        private String all;
        private String byId;
        private String byCustomer;
        private String between;
        private String byType;
        private String betweenByType;

        public String getByType() {
            return byType;
        }

        public void setByType(String byType) {
            this.byType = byType;
        }

        public String getBetweenByType() {
            return betweenByType;
        }

        public void setBetweenByType(String betweenByType) {
            this.betweenByType = betweenByType;
        }

        public String getBetween() {
            return between;
        }

        public void setBetween(String between) {
            this.between = between;
        }

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

        public String getByCustomer() {
            return byCustomer;
        }

        public void setByCustomer(String byCustomer) {
            this.byCustomer = byCustomer;
        }
    }

    public static class Update {
        private String byId;

        public String getById() {
            return byId;
        }

        public void setById(String byId) {
            this.byId = byId;
        }
    }

    public static class Delete {
        private String byId;

        public String getById() {
            return byId;
        }

        public void setById(String byId) {
            this.byId = byId;
        }
    }
}
