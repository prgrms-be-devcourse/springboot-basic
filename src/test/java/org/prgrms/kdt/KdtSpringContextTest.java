package org.prgrms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;


// Junit과 상호작용하면서 ApplicationContext 만들어지게 하기 위해서는 SPRING Extension을 사용해야합니다.
@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class KdtSpringContextTest {

    @Autowired
    ApplicationContext context;

    @Test
    @DisplayName("applicationContext가 생성되어야 한다.")
    public void testApplicationContext() {
        assertThat(context, notNullValue());
    }
}
