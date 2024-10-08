package com.store_management_tool.management_tool.service;

import com.store_management_tool.management_tool.dto.keycloak.AuthorizationRequest;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @Autowired
    private KeycloakService keycloakService;
    public String login(AuthorizationRequest authorizationRequest){
        log.info("This is a sample INFO message");

        return keycloakService.getToken(authorizationRequest);
    }
}
