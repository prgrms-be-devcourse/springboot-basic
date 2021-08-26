package org.prgrms.kdt.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

@Component //자동으로 빈으로 등록
@ConfigurationProperties(prefix = "kdt")//kdt하위에 있는 것들이 알아서 바인딩됨 , springboot
//큰 프로젝트에서 그룹화시켜서 사용한다. 작은프로젝트에선 value로도 충분함!
public class OrderProperties implements InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(OrderProperties.class);

    //@Value("v1.1.1") // 생성자를 쓰지않고도 version에 값이 주입이 된다.
    private String version;


    //Property와 @Value
    //@Value("${kdt.version}")//이렇게 프로퍼티를 전달할 수도 있음. (기본 스트링을 해당 타입에 맞게 변환해서 들어감)
    //없는 키를 전달하면 스트링이 통째로 들어가는 불상사발생
    //키를 찾지못하면 default값으로 들어가라는 명령어가 가능.
    //@Value("${kdt.version:v0.0.0}")
    //환경변수를 직접 가져올 수도 있음. (심지어 작성된 변수보다 자바의 환경변수가 우선순위가 높다.)
    //$ echo $JAVA_HOME -> @Value("${JAVA_HOME}")
    //@Value는 필드에서만 쓰는 것이아니라 생성자 함수에서 파라미터에 작성 할 수도 있음.

    //@Value("${kdt.minimum-order-amount}")
    private int minimumOrderAmount;

    private List<String> supportVendors;

    private String description;
    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("[OrderProperties] Version -> {}", version);
        logger.debug("[OrderProperties] minimumOrderAmount -> {}", minimumOrderAmount);
        logger.debug("[OrderProperties] supportVendors -> {}", supportVendors);
        logger.debug("[OrderProperties] description -> {}", description);
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
