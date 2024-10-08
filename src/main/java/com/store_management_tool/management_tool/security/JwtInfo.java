package com.store_management_tool.management_tool.security;

import com.store_management_tool.management_tool.common.AuthorizationInfo;
import com.store_management_tool.management_tool.common.Groups;
import com.store_management_tool.management_tool.common.Roles;
import com.store_management_tool.management_tool.handler.exception.NoResourceFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JwtInfo {
    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;

    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;

    public boolean isAuthorized(JwtAuthenticationToken jwt, List<AuthorizationInfo> authorizationInfos){
        boolean found = false;
        int position = 0;
        String role = extractResourceRoles(jwt.getToken()).stream()
                .map(Object::toString).findFirst()
                .orElseThrow(NoResourceFoundException::new);

        String group = extractResourceGroup(jwt.getToken()).stream()
                .map(e -> e.toString().replace("/", ""))
                .findFirst().orElseThrow(NoResourceFoundException::new);

        while (!found && position < authorizationInfos.size())
        {
            AuthorizationInfo authorizationInfo = authorizationInfos.get(position);
            if (authorizationInfo.getRole().equals(role) && authorizationInfo.getGroup().equals(group))
                found = true;
            position++;
        }

        return found;
    }
    public boolean isAuthorized(JwtAuthenticationToken jwt, List<String> authorizedRole, String authorizedGroup){
        return checkAuthorizedRole(jwt, authorizedRole) && checkAuthorizedGroup(jwt, authorizedGroup);
    }

    private boolean checkAuthorizedRole(JwtAuthenticationToken jwt, List<String> authorizedRole){
        List<String> roles = extractResourceRoles(jwt.getToken()).stream().map(Object::toString).toList();
        return !Collections.disjoint(
                Arrays.stream(Roles.values()).map(Enum::toString).toList(),
                roles)
                && authorizedRole.contains(roles.stream().findFirst().get());
    }

    private boolean checkAuthorizedGroup(JwtAuthenticationToken jwt, String authorizedGroup){
        List<String> groups = extractResourceGroup(jwt.getToken()).stream()
                .map(e -> e.toString().replace("/", ""))
                .toList();

        return !Collections.disjoint(
                Arrays.stream(Groups.values()).map(Enum::toString).toList(),
                groups)
                && authorizedGroup.equals(groups.stream().findFirst().get());
    }
    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (jwt.getClaim("resource_access") == null) {
            return Set.of();
        }
        resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess.get(resourceId) == null) {
            return Set.of();
        }
        resource = (Map<String, Object>) resourceAccess.get(resourceId);

        resourceRoles = (Collection<String>) resource.get("roles");
        return resourceRoles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    private Collection<? extends GrantedAuthority> extractResourceGroup(Jwt jwt) {
        List<String> resourceAccess;
        if (jwt.getClaim("groups") == null) {
            return Set.of();
        }
        resourceAccess = jwt.getClaim("groups");

        return resourceAccess
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
