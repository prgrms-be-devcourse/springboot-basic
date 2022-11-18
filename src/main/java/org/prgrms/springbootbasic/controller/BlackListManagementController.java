package org.prgrms.springbootbasic.controller;

import org.prgrms.springbootbasic.message.Response;
import org.prgrms.springbootbasic.service.BlackListManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.prgrms.springbootbasic.type.MethodType.LOOKUP;

@Component
public class BlackListManagementController implements Controller {

    public static final String ONLY_LOOKUP_NOTIFICATION = "you can only lookup blackList users";
    private final BlackListManagementService blackListManagementService;

    @Autowired
    public BlackListManagementController(BlackListManagementService blackListManagementService) {
        this.blackListManagementService = blackListManagementService;
    }

    @Override
    public Response process() {
        System.out.println(ONLY_LOOKUP_NOTIFICATION);
        return new Response(LOOKUP, blackListManagementService.lookupList());
    }
}
