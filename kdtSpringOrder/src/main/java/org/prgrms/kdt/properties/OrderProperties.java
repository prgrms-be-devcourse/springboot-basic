package org.prgrms.kdt.properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;

//큰 프로젝트에서 프로퍼티를 그룹화 시켜서 타입, 클래스로 정의하고 사용하는 쪽에서 주입받을 수 있을 때 많이 쓴다.
//OrderProperties 처럼 직접 관리해서 쓰는 큰 이유는 프로퍼티가 많을 때 속성이 다양화 해서 그룹화를 많이 한다
//db 관련 Properties, API 관련 Properties, server 관련 Properties 등등

//@ConfigurationProperties
//ConfigurationProperties은 SpringBoot 에서 제공해주는 어노테이션으로
//@EnableConfigurationProperties을 AppConfiguration 에 설정해줘야한다.

@Component
@ConfigurationProperties(prefix = "data") //application.yml data 하위 키값
public class OrderProperties implements InitializingBean { //for YAML

    private String version;

    private int minimumOrderAmount;

    //yaml 은 list 바인딩이 바로 되지 않음.
    private List<String> supportVendors;

    private String description;

    @Value("${JAVA_HOME}")
    private String javaHome;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MessageFormat.format("[OrderProperties] version -> {0}", version));
        System.out.println(MessageFormat.format("[OrderProperties] minimumOrderAmount -> {0}", minimumOrderAmount));
        System.out.println(MessageFormat.format("[OrderProperties] supportVendors -> {0}", supportVendors));
        System.out.println(MessageFormat.format("[OrderProperties] description -> {0}", description));
        System.out.println(MessageFormat.format("[OrderProperties] javaHome -> {0}", javaHome));
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