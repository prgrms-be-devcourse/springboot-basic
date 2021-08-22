package org.prgrms.kdt.order.property;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
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
@Component
public class OrderProperties implements InitializingBean {
    // @Value("v1.1.1")
    // @Value("${kdt.version}") // 실질적으로 value를 properties에서 키를 주입하여 할당. 대신 없는 값을 주면 "${kdt.version}" 이게 고대로 들어감
    @Value("${kdt.version2:v0.0.0}") // 그래서 위의 문제를 해결하기위해 값이 없다면 default 값을 넣어라는 것을 지정할(: <- 이걸로) 수 있음
    // @Value("${JAVA_HOME}")// properties를 가져올때 시스템의 환경변수를 가져오는 방법임.
    // 만약 key가 property 파일과 환경변수의 키가 같다면 (시스템) 환경 변수의 설정값들이 우선순위가 큼.
    private String version;

    // @Value("0")
    @Value("${kdt.minimumOrderAmount}")
    private int minimumOrderAmount;

    // @Value("d, a, b")
    @Value("${kdt.supportVendors}")
    private List<String> supportVendors;




    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println(MessageFormat.format("[OrderProperties] version -> {0}", version ));
        System.out.println(MessageFormat.format("[OrderProperties] minimumOrderAmount -> {0}", minimumOrderAmount ));
        System.out.println(MessageFormat.format("[OrderProperties] supportVendors -> {0}", supportVendors ));
    }
}
