package org.prgrms.orderApp.presentation.commandOperator.script;

import org.prgrms.orderApp.infrastructure.library.console.script.BasicScript;
import org.prgrms.orderApp.infrastructure.library.console.script.ForUxScript;
import org.springframework.stereotype.Component;

@Component
public interface AllScriptForCMDApplication extends ApplicationScript, ForUxScript, WarnningAndErrorScript, BasicScript, MonguDbScript {

}
