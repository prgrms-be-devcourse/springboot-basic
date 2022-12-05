package org.prgrms.springorder.common;

import org.prgrms.springorder.CommandLineApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@ActiveProfiles("dev")
public class ControllerIntegrationBase {

    @MockBean
    private CommandLineApplication commandLineApplication;

}
