package com.store_management_tool.management_tool.controller;

import com.store_management_tool.management_tool.dto.keycloak.AuthorizationRequest;
import com.store_management_tool.management_tool.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "User", description = "User endpoints")
@RequestMapping("v1")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthorizationRequest authorizationRequest) {
        return new ResponseEntity<>(userService.login(authorizationRequest), HttpStatus.OK);
    }
}
