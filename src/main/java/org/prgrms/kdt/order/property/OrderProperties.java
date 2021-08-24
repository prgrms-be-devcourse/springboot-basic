package org.prgrms.kdt.order.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

/**
 * 다른 컴포넌트에 프로퍼티를 쓰고 싶을 때, 그럴때마다 Enviornment에서 주입받아서 쓸 수 있지만,
 * Property에다가 Key에 대한 값을 Order의 Field에 주입시킬 수 있는 방법은 @Value를 사용하면 됩니다.
 *
 * 값이 잘 주입이 되었는지 확인하고 싶으면 OrderTest에 getBean으로 가져와서 확인할 수 있는데,
 * 요 안에서 확인할 수 있는 방법이 annotation을 이용합니다.-> InitializingBean을 사용하면 필드에 접근하여 확인해볼 수 있습니다.
 */
// yaml에서 배열형태로 된게 spring boot에서 인식이 안됩니다. 그래서 저희는 OrderProperties를 Component에서 ConfigurationProperty로 바꿀꺼에옹
@Configuration
@ConfigurationProperties(prefix = "kdt") // spring boot에서 나온 것. 그래서 Appconfigt에서 @EnableConfiguration 달아줘야함
public class OrderProperties implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(OrderProperties.class);

    private String version;
    private int minimumOrderAmount;
    private List<String> supportVendors;
    private String description;

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("[OrderProperties] version -> {}", version );
        logger.debug("[OrderProperties] minimumOrderAmount -> {}", minimumOrderAmount );
        logger.debug("[OrderProperties] supportVendors -> {}", supportVendors );
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getMinimumOrderAmount() {
        return minimumOrderAmount;
    }

    public void setMinimumOrderAmount(int minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    public List<String> getSupportVendors() {
        return supportVendors;
    }

    public void setSupportVendors(List<String> supportVendors) {
        this.supportVendors = supportVendors;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
